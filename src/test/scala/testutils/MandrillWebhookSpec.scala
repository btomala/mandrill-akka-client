package testutils

import mandrillclient.api.ErrorResponse
import mandrillclient.api.Templates.{DeleteTemplate, TemplateResponse}
import mandrillclient.api.Webhooks.{DeleteWebhook, AddWebhook, ListWebhook, WebhookResponse}

import scala.concurrent.{Await, Future}

class MandrillWebhookSpec extends MandrillClientSpec {

  val testWebhookDesc = "integration-test-" + suiteName

  private[testutils] trait TestWebhook {
    val url = "mandrill.com"
    val addWebhook = AddWebhook(apiKey, url, Some(testWebhookDesc), Set())
    val t = apiActor ? addWebhook
  }

  protected def withWebhook(f: Int => Unit) = new TestWebhook {
    import system.dispatcher
    t.map {
      case e: Right[_,WebhookResponse] => f(e.right.value.id)
    }
  }

  private def cleanWebhooks(after: => Unit) = {
    import system.dispatcher
    val responseInFuture = apiActor ? ListWebhook(apiKey)
    val response = Await.result(responseInFuture, duration).asInstanceOf[Either[ErrorResponse, Seq[WebhookResponse]]]
    response shouldBe a [Right[ErrorResponse, Seq[WebhookResponse]]]
    val list = response.right.value.filter(w => w.description.isDefined && w.description.get == testWebhookDesc)
    info(list.toString)
    Future.sequence(list.map(w => apiActor ? DeleteWebhook(apiKey, w.id))) map {
      case deleted: Seq[Either[ErrorResponse, WebhookResponse]] => after
    }
  }

  override def afterAll() = {
    if(settings.templatesClean) {
      info("remove webhooks with description: " + testWebhookDesc)
      cleanWebhooks(super.afterAll())
      info("webhooks are removed")
    } else {
      super.afterAll()
    }
  }

}
