package testutils

import akka.actor.{Props, ActorSystem}
import akka.pattern.AskSupport
import akka.stream.ActorMaterializer
import akka.testkit.{ImplicitSender, DefaultTimeout, TestKit}
import mandrillclient.api.JsonFormats
import mandrillclient.core.{MandrillAPIActor, MandrillClientSettings, Settings}
import org.scalatest._
import scala.concurrent.duration._

class MandrillClientSpec extends TestKit(ActorSystem("mandrill")) with DefaultTimeout with ImplicitSender
  with WordSpecLike with Matchers with BeforeAndAfterAll with AskSupport with EitherValues with OptionValues {

  implicit val materializer = ActorMaterializer()

  override def afterAll() = {
    info.apply("shutdown actor system")
    system.shutdown()
    super.afterAll()
  }

  val settings = new Settings with MandrillClientSettings
  val apiKey = settings.testKey

  val duration = 5 seconds
  val apiActor = system.actorOf(Props(classOf[MandrillAPIActor], settings, system, JsonFormats.formats), "Test-MandrillAPIActor")

}
