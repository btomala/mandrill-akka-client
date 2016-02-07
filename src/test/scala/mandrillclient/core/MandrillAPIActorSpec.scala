package mandrillclient.core

import akka.actor.{Props, ActorSystem}
import akka.pattern.AskSupport
import akka.stream.ActorMaterializer
import akka.testkit.{ImplicitSender, DefaultTimeout, TestKit}
import mandrillclient.api.ErrorResponse
import mandrillclient.api.Messages.{SendResponse, SendTo, SendMessage, Send}
import mandrillclient.api.Users.{Ping2, Ping2Response}
import mandrillclient.api.constants.{ErrorName, SendStatus, SendToType}
import org.scalatest.{EitherValues, BeforeAndAfterAll, Matchers, WordSpecLike}

import scala.concurrent.Await
import scala.concurrent.duration._

class MandrillAPIActorSpec extends TestKit(ActorSystem("mandrill")) with DefaultTimeout with ImplicitSender
    with WordSpecLike with Matchers with BeforeAndAfterAll with AskSupport with EitherValues {

    implicit val materializer = ActorMaterializer()

    override def afterAll() = system.shutdown()

    val settings = new Settings with MandrillClientSettings

    val duration = 5 seconds
    val apiActor = system.actorOf(Props(classOf[MandrillAPIActor], settings, system), "Test-MandrillAPIActor")

    "Mandrill api actor " when {
      "when receive 'Ping2' message with valid key" should {
        "respond with 'Ping2Response' object" in {
          val responseInFuture = apiActor ? Ping2(settings.key)
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
      "when receive 'Send' message with valid key" should {
        "respond with 'SendResponse' object" in {
          val message = SendMessage(None, None, "Test", settings.testEmail, "Tester", Seq(SendTo(settings.testEmail, "Tester", SendToType.to)))
          val responseInFuture = apiActor ? Send(settings.key, message)
          val response = Await.result(responseInFuture, duration).asInstanceOf[Either[ErrorResponse, SendResponse]]
          println(response)
          response shouldBe a [Right[ErrorResponse, SendResponse]]
          response.right.value.status shouldBe SendStatus.sent
        }
      }
    }
}
