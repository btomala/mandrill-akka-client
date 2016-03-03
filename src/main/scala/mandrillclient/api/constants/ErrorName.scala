package mandrillclient.api.constants

object ErrorName extends Enumeration {
  type ErrorName = Value
  /** The provided API key is not a valid Mandrill API key */
  val InvalidKey = Value("Invalid_Key") //-1
  /** The requested feature requires payment. */
  val PaymentRequired = Value("PaymentRequired")
  /** The requested webhook does not exist */
  val UnknownWebhook = Value("Unknown_Webhook") //3
  /** The provided subaccount id does not exist. */
  val UnknownSubaccount = Value("Unknown_Subaccount")
  /** The requested template does not exist */
  val UnknownTemplate = Value("Unknown_Template") //5
  /** The given template name already exists or contains invalid characters */
  val InvalidTemplate = Value("Invalid_Template") //6 - A template with name "xxx" already exists
  /** The parameters passed to the API call are invalid or not provided when required */
  val ValidationError = Value("ValidationError") //-2
  /** An unexpected error occurred processing the request. Mandrill developers will be notified. */
  val GeneralError = Value("GeneralError")
  /**  The provided message id does not exist */
  val UnknownMessage = Value("Unknown_Message") //11
}
