package testutils

import java.util.UUID

import mandrillclient.api.ErrorResponse
import mandrillclient.api.Templates.{List, Delete, TemplateResponse, AddTemplate}

import scala.concurrent.{Future, Await}

class MandrillTemplateSpec extends MandrillClientSpec {

  val testMandrillLabel = "mandrill-akka-http-integration-test"

  protected trait TestTemplate {
    val name: String
    val code = "<h1>{{testTitle}}</h1>"
    val addTemplate = AddTemplate(apiKey, name, settings.testEmail, "Tester", "Test", code = Some(code), text = None, Seq(testMandrillLabel))
    val t = apiActor ? addTemplate
  }

  protected def withTemplate(maybeName: String = null)(f: String => Unit) = new TestTemplate {
    val name = Option(maybeName).getOrElse(UUID.randomUUID().toString)
    import system.dispatcher
    t.map(_ => f(name))
  }

  def cleanTemplates() = {
    import system.dispatcher
    val responseInFuture = apiActor ? List(apiKey, testMandrillLabel)
    val response = Await.result(responseInFuture, duration).asInstanceOf[Either[ErrorResponse, Seq[TemplateResponse]]]
    response shouldBe a [Right[ErrorResponse, Seq[TemplateResponse]]]
    val list = response.right.value
    val deleted = Await.result(Future.sequence(list.map(t => apiActor ? Delete(apiKey, t.name))), duration).asInstanceOf[Seq[Either[ErrorResponse, TemplateResponse]]]
    deleted.foreach(p => p) //it is required to wait for execution
  }

  override def afterAll() = {
    info.apply("remove template with label: " + testMandrillLabel)
    cleanTemplates()
    info.apply("templates are removed")
    super.afterAll()
  }

}
