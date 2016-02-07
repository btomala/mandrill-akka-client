package mandrillclient.api

import mandrillclient.api.constants.MargeLanguage._
import mandrillclient.api.constants.RejectReason.RejectReason
import mandrillclient.api.constants.SendStatus.SendStatus
import mandrillclient.api.constants.SendToType.SendToType

import scala.collection.immutable.HashMap

object Messages {
  case class Send(key: String,
                  message: SendMessage,
                  async: Boolean = true,
                  ip_pool: Option[String] = None,
                  sand_at: Option[String] = None) extends MandrillRequest
  case class SendResponse(email: String, status: SendStatus, _id: String, reject_reason: RejectReason)

  case class SendTo(email: String, name: String, `type`: SendToType)
  case class GlobalMargeVars(name: String, content: String)
  case class MargeVars(rcpt: String, vars: Seq[GlobalMargeVars])
  case class RecipientMetadata(rcpt: String, values: HashMap[String, Any])
  case class SendFile(`type`: String, name: String, content: String)
  case class SendMessage(
                        html: Option[String],
                        text: Option[String],
                        subject: String,
                        from_email: String,
                        from_name: String,
                        to: Seq[SendTo],
                        headers: HashMap[String, String] = HashMap(), //ex   "Reply-To"
                        important: Boolean = false,
                        track_opens: Boolean = false,
                        track_clicks: Boolean = false,
                        auto_text: Boolean = false,
                        auto_html: Boolean = false,
                        inline_css: Boolean = false,
                        url_strip_qs: Boolean = false,
                        preserve_recipients: Boolean = true,
                        view_content_link: Boolean = true,
                        bcc_address: Option[String] = None,
                        tracking_domain: Option[String] = None,
                        signing_domain: Option[String] = None,
                        return_path_domain: Option[String] = None,
                        merge: Boolean = true,
                        merge_language: MargeLanguage = mailchimp,
                        global_merge_vars: Seq[GlobalMargeVars] = Seq(),
                        merge_vars: Seq[MargeVars] = Seq(),
                        tags: Seq[String] = Seq(),
                        subaccount: Option[String] = None,
                        google_analytics_domains: Seq[String] = Seq(),
                        google_analytics_campaign: Seq[String] = Seq(),
                        metadata: HashMap[String,String] = HashMap(),
                        recipient_metadata: Seq[RecipientMetadata] = Seq(),
                        attachments: Seq[SendFile] = Seq(),
                        images: Seq[SendFile] = Seq()
                        )

}
