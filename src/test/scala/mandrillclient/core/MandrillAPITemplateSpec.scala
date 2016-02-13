package mandrillclient.core

import java.util.UUID

import mandrillclient.api.ErrorResponse
import mandrillclient.api.Messages.{Send, SendMessage, SendResponse, SendTo}
import mandrillclient.api.Templates._
import mandrillclient.api.Users.{Ping2, Ping2Response}
import mandrillclient.api.constants.{ErrorName, SendStatus, SendToType}
import testutils.{MandrillTemplateSpec, MandrillClientSpec}

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global

class MandrillAPITemplateSpec extends MandrillTemplateSpec {

  "Mandrill api actor " when {
    "when receive 'AddTemplate' message with valid key" should {
      "respond with 'TemplateResponse' object" in {
        val name = "Add test template"
        val code = "<h1>{{testTitle}}</h1>"
        val addTemplate = AddTemplate(apiKey, name, settings.testEmail, "Tester", "Test", code = Some(code), text = None, Seq("test", "code"))
        val responseInFuture = apiActor ? addTemplate
        val response = Await.result(responseInFuture, duration).asInstanceOf[Either[ErrorResponse, TemplateResponse]]
        response shouldBe a [Right[ErrorResponse, TemplateResponse]]
        response.right.value.name shouldBe name
        response.right.value.code.value shouldBe code
      }
    }
    "when receive 'UpdateTemplate' message with valid key" should {
      "respond with 'TemplateResponse' object" in withTemplate { name =>
        val code = "<h1>{{testTitle}}</h1><div>{{content}}</div>"
        val updateTemplate = UpdateTemplate(apiKey, name, settings.testEmail, "Tester", "Test", code = Some(code), text = None, Seq("test", "code"))
        val responseInFuture = apiActor ? updateTemplate
        val response = Await.result(responseInFuture, duration).asInstanceOf[Either[ErrorResponse, TemplateResponse]]
        response shouldBe a [Right[ErrorResponse, TemplateResponse]]
        response.right.value.name shouldBe name
        response.right.value.code.value shouldBe code
      }
    }
    "when receive 'Info' message with valid key and name of template" should {
      "fetch information about template" in withTemplate { name =>
        val responseInFuture = apiActor ? Info(apiKey, name)
        val response = Await.result(responseInFuture, duration).asInstanceOf[Either[ErrorResponse, TemplateResponse]]
        response shouldBe a [Right[ErrorResponse, TemplateResponse]]
        response.right.value.name shouldBe name
      }
    }
    "when receive 'Info' message with valid key and not exist name of template" should {
      "respond with Unknown Template message" in {
        val name = "Info not exist test template"
        val responseInFuture = apiActor ? Info(apiKey, name)
        val response = Await.result(responseInFuture, duration).asInstanceOf[Either[ErrorResponse, TemplateResponse]]
        response shouldBe a [Left[ErrorResponse, TemplateResponse]]
        response.left.value.name shouldBe ErrorName.UnknownTemplate
      }
    }
    "when receive 'Delete' message with valid key" should {
      "respond with 'TemplateResponse' object" in withTemplate { name =>
        val responseInFuture = apiActor ? Delete(apiKey, name)
        val response = Await.result(responseInFuture, duration).asInstanceOf[Either[ErrorResponse, TemplateResponse]]
        response shouldBe a [Right[ErrorResponse, TemplateResponse]]
        response.right.value.name shouldBe name
      }
    }
  }
}
