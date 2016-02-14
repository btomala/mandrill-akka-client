package testutils

import java.util.UUID

import mandrillclient.api.ErrorResponse
import mandrillclient.api.Templates.{List, Delete, TemplateResponse, AddTemplate}

import scala.concurrent.{Future, Await}

class MandrillTemplateSpec extends MandrillClientSpec {

  val testMandrillLabel = "integration-test-" + suiteName

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

  private def cleanTemplates(after: => Unit) = {
    import system.dispatcher
    val responseInFuture = apiActor ? List(apiKey, testMandrillLabel)
    val response = Await.result(responseInFuture, duration).asInstanceOf[Either[ErrorResponse, Seq[TemplateResponse]]]
    response shouldBe a [Right[ErrorResponse, Seq[TemplateResponse]]]
    val list = response.right.value
    Future.sequence(list.map(t => apiActor ? Delete(apiKey, t.name))) map {
      case deleted: Seq[Either[ErrorResponse, TemplateResponse]] => after
    }
  }

  override def afterAll() = {
    if(settings.templatesClean) {
      info.apply("remove template with label: " + testMandrillLabel)
      cleanTemplates(super.afterAll())
      info.apply("templates are removed")
    } else {
      super.afterAll()
    }

  }

}
