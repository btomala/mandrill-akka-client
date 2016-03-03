package mandrillclient.api

import mandrillclient.api.constants.MargeLanguage._
import mandrillclient.api.constants.RejectReason.RejectReason
import mandrillclient.api.constants.SendStatus.SendStatus
import mandrillclient.api.constants.SendToType.SendToType

import scala.collection.immutable.HashMap

object Messages {
  case class Content(key: String, id: String) extends MandrillRequest
  case class Send(key: String,
                  message: SendMessage,
                  async: Boolean = true,
                  ip_pool: Option[String] = None,
                  sand_at: Option[String] = None) extends MandrillRequest
  case class SendTemplate(key: String,
                          template_name: String,
                          message: SendMessage,
                          template_content: Seq[EditableRegion] = Seq(),
                          async: Boolean = true,
                          ip_pool: Option[String] = None,
                          sand_at: Option[String] = None) extends MandrillRequest
  case class SendResponse(email: String, status: SendStatus, _id: String, reject_reason: Option[RejectReason])
  case class ContentResponse(ts: Int,
                             _id: String,
                             from_email: String,
                             from_name: Option[String],
                             subject: String,
                             to: Seq[Recipient],
                             tags: Seq[String],
                             headers: HashMap[String, String],
                             text: Option[String],
                             html: Option[String],
                             attachments: Seq[SendFile]
                            )

  case class Recipient(email: String, name: Option[String])
  case class SendTo(email: String, name: String, `type`: SendToType)

  /**
    * @param name the name of the mc:edit editable region to inject into
    * @param content the content to inject
    */
  case class EditableRegion(name: String, content: String)
  case class GlobalMargeVars(name: String, content: String)
  case class MargeVars(rcpt: String, vars: Seq[GlobalMargeVars])
  case class RecipientMetadata(rcpt: String, values: HashMap[String, Any])
  case class SendFile(`type`: String, name: String, content: String)
  case class SendMessage(
                        to: Seq[SendTo],
                        from_email: Option[String] = None,
                        from_name: Option[String] = None,
                        html: Option[String] = None,
                        text: Option[String] = None,
                        subject: Option[String] = None,
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
