package mandrillclient.core

import akka.actor.{Props, ActorSystem}
import akka.pattern.AskSupport
import akka.stream.ActorMaterializer
import akka.testkit.{ImplicitSender, DefaultTimeout, TestKit}
import mandrillclient.api.ErrorResponse
import mandrillclient.api.Users.Ping2Response
import mandrillclient.core.MandrillAPIActor.Ping2
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}

import scala.concurrent.Await
import scala.concurrent.duration._

class MandrillAPIActorSpec extends TestKit(ActorSystem("mandrill")) with DefaultTimeout with ImplicitSender
    with WordSpecLike with Matchers with BeforeAndAfterAll with AskSupport {

    implicit val materializer = ActorMaterializer()

    override def afterAll() = system.shutdown()

    val settings = new Settings with MandrillClientSettings

    val duration = 5 seconds
    val apiActor = system.actorOf(Props(classOf[MandrillAPIActor], settings, system))

    "Mandrill api actor " when {
      "when receive 'Ping2' message with valid key" should {
        "respond with 'Ping2Response' object" in {
          val responseInFuture = apiActor ? Ping2(settings.key)
          val response = Await.result(responseInFuture, duration)
          response shouldBe a [Right[Ping2Response, ErrorResponse]]
        }
      }
      "when receive 'Ping2' message with invalid key" should {
        "respond with 'ErrorResponse' object" in {
          val responseInFuture = apiActor ? Ping2("s")
          val response = Await.result(responseInFuture, duration)
          response shouldBe a [Left[Ping2Response, ErrorResponse]]
        }
      }
    }
}
