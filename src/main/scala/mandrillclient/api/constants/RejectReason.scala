package mandrillclient.api.constants

object RejectReason extends Enumeration {
  type RejectReason = Value
  val hardBounce = Value("hard-bounce")
  val softBounce = Value("soft-bounce")
  val spam = Value("spam")
  val unsub = Value("unsub")
  val custom = Value("custom")
  val invalidSender = Value("invalid-sender")
  val invalid = Value("invalid")
  val testModeLimit = Value("test-mode-limit")
  val unsigned = Value("unsigned")
  val rule = Value("rule")
}
