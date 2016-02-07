package mandrillclient.api.constants

object SendToType extends Enumeration {
  type SendToType = Value
  val to, cc, bcc = Value
}