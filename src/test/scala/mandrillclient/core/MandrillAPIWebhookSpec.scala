package mandrillclient.core

import mandrillclient.api.ErrorResponse
import mandrillclient.api.Webhooks._
import mandrillclient.api.constants.ErrorName
import testutils.{MandrillWebhookSpec, MandrillClientSpec}

import scala.concurrent.Await

class MandrillAPIWebhookSpec extends MandrillWebhookSpec {

  "Mandrill api actor " when {
    "when receive 'AddWebhook' message with valid key" should {
      "add webhook and respond with Webhook object" in {
        val url = "mandrill.com"
        val addWebhook = AddWebhook(apiKey, url, Some(testWebhookDesc), Set())
        val responseInFuture = apiActor ? addWebhook
        val response = Await.result(responseInFuture, duration).asInstanceOf[Either[ErrorResponse, WebhookResponse]]
        response shouldBe a [Right[_, _]]
        response.right.value.url should endWith (url)
      }
    }
    "when receive 'AddWebhook' message with valid key & incorrect uri" should {
      "not add webhook and respond with Validation Error" in {
        val url = "incorrect-webhook-url"
        val desc = "test-webhook"
        val addWebhook = AddWebhook(apiKey, url, Some(desc), Set())
        val responseInFuture = apiActor ? addWebhook
        val response = Await.result(responseInFuture, duration).asInstanceOf[Either[ErrorResponse, WebhookResponse]]
        response shouldBe a [Left[_, _]]
        response.left.value.name shouldBe ErrorName.ValidationError
      }
    }
    "when receive 'ListWebhook' message with valid key" should {
      "add webhook and respond with Webhook object" in withWebhook { id =>
        val listWebhook = ListWebhook(apiKey)
        val responseInFuture = apiActor ? listWebhook
        val response = Await.result(responseInFuture, duration).asInstanceOf[Either[ErrorResponse, Seq[WebhookResponse]]]
        response shouldBe a [Right[_, _]]
        response.right.value.size should be >= 1
        response.right.value.size
      }
    }
    "when receive 'DeleteWebhook' message with valid key" should {
      "add webhook and respond with Webhook object" in withWebhook { id =>
        info(id.toString)
        val deleteWebhook = DeleteWebhook(apiKey, id)
        val responseInFuture = apiActor ? deleteWebhook
        val response = Await.result(responseInFuture, duration).asInstanceOf[Either[ErrorResponse, WebhookResponse]]
        response shouldBe a [Right[_, _]]
        response.right.value.id shouldBe id
      }
    }
    "when receive 'InfoWebhook' message with valid key" should {
      "add webhook and respond with Webhook object" in withWebhook { id =>
        val infoWebhook = InfoWebhook(apiKey, id)
        val responseInFuture = apiActor ? infoWebhook
        val response = Await.result(responseInFuture, duration).asInstanceOf[Either[ErrorResponse, WebhookResponse]]
        response shouldBe a [Right[_, _]]
        response.right.value.id shouldBe id
      }
    }

  }
}
