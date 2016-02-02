package mandrillclient.api


package object users {

  case class UserRequest(key: String)

  case class Ping2Response(PING: String)
}
