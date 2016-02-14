package mandrillclient.core

import mandrillclient.api.ErrorResponse
import mandrillclient.api.Users.{Ping2, Ping2Response}
import mandrillclient.api.constants.{ErrorName}
import testutils.MandrillClientSpec

import scala.concurrent.Await

class MandrillAPIUserSpec extends MandrillClientSpec {

  "Mandrill api actor " when {
    "when receive 'Ping2' message with valid key" should {
      "respond with 'Ping2Response' object" in {
        val responseInFuture = apiActor ? Ping2(apiKey)
        val response = Await.result(responseInFuture, duration).asInstanceOf[Either[ErrorResponse, Ping2Response]]
        response shouldBe a [Right[ErrorResponse, Ping2Response]]
        response.right.value.PING shouldBe "PONG!"
      }
    }
    "when receive 'Ping2' message with invalid key" should {
      "respond with 'ErrorResponse' object" in {
        val responseInFuture = apiActor ? Ping2("s")
        val response = Await.result(responseInFuture, duration).asInstanceOf[Either[ErrorResponse, Ping2Response]]
        response shouldBe a [Left[ErrorResponse, Ping2Response]]
        response.left.value.status shouldBe "error"
        response.left.value.name shouldBe ErrorName.InvalidKey
      }
    }
  }
}
