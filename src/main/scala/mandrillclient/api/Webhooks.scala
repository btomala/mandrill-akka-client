package mandrillclient.api

import mandrillclient.api.constants.WebhookEvent.WebhookEvent
import org.joda.time.DateTime

object Webhooks {
  case class ListWebhook(key: String) extends MandrillRequest
  case class AddWebhook(key: String,
                        url: String,
                        description: Option[String],
                        events: Set[WebhookEvent]) extends MandrillRequest
  case class InfoWebhook(key: String, id: Int) extends MandrillRequest
  case class DeleteWebhook(key: String, id: Int) extends MandrillRequest

  case class WebhookResponse(id: Int,
                             url: String,
                             description: Option[String],
                             auth_key: String,
                             events: Set[WebhookEvent],
                             created_at: DateTime,
                             last_sent_at: Option[DateTime],
                             batches_sent: Int,
                             events_sent: Int,
                             last_error: Option[String])
}
