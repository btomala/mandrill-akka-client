package mandrillclient.api

object Users {

  case object UserRequest extends MandrillRequest {
    val key = ""
  }
  case class Ping2Response(PING: String)

}
