package mandrillclient.api.constants

object SendStatus extends Enumeration {
  type SendStatus = Value
  val sent, queued, scheduled, rejected, invalid = Value
}
