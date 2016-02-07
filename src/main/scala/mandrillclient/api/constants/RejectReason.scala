package mandrillclient.api.constants

object RejectReason extends Enumeration {
  type RejectReason = Value
  val `hard-bounce`, `soft-bounce`, spam, unsub, custom, `invalid-sender`, invalid, `test-mode-limit`, unsigned, rule = Value
}
