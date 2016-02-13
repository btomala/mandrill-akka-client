package testutils

import java.util.UUID

import mandrillclient.api.Templates.AddTemplate

class MandrillTemplateSpec extends MandrillClientSpec {

  override def afterAll() = {
    super.afterAll()
  }

  trait TestTemplate {
    val name = UUID.randomUUID().toString
    val code = "<h1>{{testTitle}}</h1>"
    val addTemplate = AddTemplate(apiKey, name, settings.testEmail, "Tester", "Test", code = Some(code), text = None, Seq("mandrill-akka-http-integration-test"))
    val t = apiActor ? addTemplate
  }

  def withTemplate(f: String => Unit) = new TestTemplate {
    t.map(_ => f(name))
  }

}
