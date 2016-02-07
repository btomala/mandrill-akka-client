package mandrillclient.api

object Users {
  case class Ping2(key: String) extends MandrillRequest
  case class Ping2Response(PING: String)
}
