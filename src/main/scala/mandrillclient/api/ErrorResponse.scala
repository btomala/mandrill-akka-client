package mandrillclient.api

import mandrillclient.api.ErrorName.ErrorName

case class ErrorResponse(status: String, code: Int, name: ErrorName, message: String)

object ErrorName extends Enumeration {
  type ErrorName = Value
  /** The provided API key is not a valid Mandrill API key */
  val InvalidKey = Value("Invalid_Key")
  /** The parameters passed to the API call are invalid or not provided when required */
  val ValidationError = Value("ValidationError")
  /** An unexpected error occurred processing the request. Mandrill developers will be notified. */
  val GeneralError = Value("GeneralError")
}
