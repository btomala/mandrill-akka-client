package mandrillclient.api.constants

object ErrorName extends Enumeration {
  type ErrorName = Value
  /** The provided API key is not a valid Mandrill API key */
  val InvalidKey = Value("Invalid_Key")
  /**  The provided message id does not exist */
  val UnknownMessage = Value("Unknown_Message")
  /** The parameters passed to the API call are invalid or not provided when required */
  val ValidationError = Value("ValidationError")
  /** An unexpected error occurred processing the request. Mandrill developers will be notified. */
  val GeneralError = Value("GeneralError")
  /** The requested template does not exist */
  val  UnknownTemplate = Value("Unknown_Template") //5
  /** The given template name already exists or contains invalid characters */
  val InvalidTemplate = Value("Invalid_Template") //6 - A template with name "Test template" already exists
}
