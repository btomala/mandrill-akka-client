package mandrillclient.api

import org.joda.time.DateTime

object Templates {
  case class Add(key: String,
                 name: String,
                 from_email: String,
                 from_name: String,
                 subject: String,
                 code: Option[String],
                 text: Option[String],
                 labels: Seq[String] = Seq(),
                 publish: Boolean = false) extends MandrillRequest
  case class Delete(key: String, name: String) extends MandrillRequest
  case class TemplateResponse(slug: String,
                              name: String,
                              from_email: String,
                              from_name: String,
                              subject: String,
                              code: Option[String],
                              text: Option[String],
                              labels: Seq[String],
                              published_at: Option[DateTime],
                              created_at: DateTime,
                              updated_at: DateTime)
}