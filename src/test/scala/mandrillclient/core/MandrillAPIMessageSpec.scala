package mandrillclient.core

import mandrillclient.api.ErrorResponse
import mandrillclient.api.Messages._
import mandrillclient.api.constants.{RejectReason, ErrorName, SendStatus, SendToType}
import testutils.{MandrillTemplateSpec, MandrillClientSpec}

import scala.concurrent.Await

class MandrillAPIMessageSpec extends MandrillTemplateSpec {

  val rejectEmail = "reject@test.mandrillapp.com"
  val hardBounceEmail = "hard_bounce@test.mandrillapp.com"
  val softBounceEmail = "soft_bounce@test.mandrillapp.com"
  val spamEmail = "spam@test.mandrillapp.com"

  "Mandrill api actor " when {
    "receive 'Send' message with valid key" should {
      "respond with 'SendResponse' object" in {
        val message = SendMessage(Seq(SendTo(settings.testEmail, "Tester", SendToType.to)), Some(settings.testEmail))
        val responseInFuture = apiActor ? Send(apiKey, message)
        val response = Await.result(responseInFuture, duration).asInstanceOf[Either[ErrorResponse, SendResponse]]
        response shouldBe a [Right[_, _]]
        response.right.value.status shouldBe SendStatus.sent
      }
    }
    "receive 'Send' message with valid key & without sender" should {
      s"respond with send response with status rejected and reason '${RejectReason.invalidSender}'" in {
        val message = SendMessage(Seq(SendTo(settings.testEmail, "Tester", SendToType.to)))
        val responseInFuture = apiActor ? Send(apiKey, message)
        val response = Await.result(responseInFuture, duration).asInstanceOf[Either[ErrorResponse, SendResponse]]
        response shouldBe a [Right[_, _]]
        response.right.value.status shouldBe SendStatus.rejected
        response.right.value.reject_reason.value shouldBe RejectReason.invalidSender
      }
    }
    "receive 'SendTemplate' message with valid key & not exist template" should {
      "respond with error response with name " in {
        val message = SendMessage(Seq(SendTo(settings.testEmail, "Tester", SendToType.to)))
        val responseInFuture = apiActor ? SendTemplate(apiKey, "UnknownTemplate", message)
        val response = Await.result(responseInFuture, duration).asInstanceOf[Either[ErrorResponse, SendResponse]]
        response shouldBe a [Left[_, _]]
        response.left.value.name shouldBe ErrorName.UnknownTemplate
      }
    }
    "receive 'SendTemplate' message with valid key & template" should {
      "respond with error response with name " in withTemplate() { name =>
        val message = SendMessage(Seq(SendTo(settings.testEmail, "Tester", SendToType.to)))
        val responseInFuture = apiActor ? SendTemplate(apiKey, name, message)
        val response = Await.result(responseInFuture, duration).asInstanceOf[Either[ErrorResponse, SendResponse]]
        response shouldBe a [Right[_,_]]
        response.right.value.status shouldBe SendStatus.sent
      }
    }
  }
}
