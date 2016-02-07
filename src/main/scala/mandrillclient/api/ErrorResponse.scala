package mandrillclient.api

import mandrillclient.api.constants.ErrorName.ErrorName

case class ErrorResponse(status: String, code: Int, name: ErrorName, message: String)