package mandrillclient.api.constants

object WebhookEvent extends Enumeration {
  type WebhookEvent = Value
  /** message has been sent successfully*/
  val send = Value("send")
  /** message has been sent, but the receiving server has indicated mail is being delivered too quickly and Mandrill should slow down sending temporarily */
  val deferral = Value("deferral")
  /** message has hard bounced */
  val hardBounce = Value("hard_bounce")
  /** message has soft bounced */
  val softBounce = Value("soft_bounce")
  /** recipient opened a message; will only occur when open tracking is enabled */
  val open = Value("open")
  /** recipient clicked a link in a message; will only occur when click tracking is enabled */
  val click = Value("click")
  /** recipient marked a message as spam */
  val spam = Value("spam")
  /** recipient unsubscribed */
  val unsub = Value("unsub")
  /** message was rejected */
  val reject = Value("reject")
  /** triggered when a Rejection Blacklist entry is added, changed, or removed */
  val blacklist = Value("blacklist")
  /** triggered when a Rejection Whitelist entry is added or removed */
  val whitelist = Value("whitelist")
}
