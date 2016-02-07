package mandrillclient.core

import akka.actor.{ActorSystem, Actor}
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.pattern.PipeToSupport
import akka.stream.ActorMaterializer
import mandrillclient.api.ErrorResponse
import mandrillclient.api.Messages.{SendResponse, Send}
import mandrillclient.api.Users.{Ping2, Ping2Response}
import de.heikoseeberger.akkahttpjson4s.Json4sSupport._
import mandrillclient.api.constants._
import org.json4s.{native, DefaultFormats}

import scala.concurrent.Future
import scala.util.{Left, Right, Either}
import org.json4s.ext.EnumNameSerializer

class MandrillAPIActor(settings: MandrillClientSettings)(implicit system: ActorSystem) extends Actor with PipeToSupport {

  implicit val materializer = ActorMaterializer()
  implicit val formats = DefaultFormats + new EnumNameSerializer(ErrorName) +
                         new EnumNameSerializer(SendToType) + new EnumNameSerializer(SendStatus) +
                         new EnumNameSerializer(MargeLanguage) + new EnumNameSerializer(RejectReason)
  implicit val serialization = native.Serialization

  import system.dispatcher
  private val http = Http()
  private val logger = Logging(context.system, this)
  private val ping2Uri = Uri(settings.endpoint + settings.ping2)
  private val sendUri = Uri(settings.endpoint + settings.send)

  private def flatEitherCond[A, B](test: Boolean, right: => Future[B], left: => Future[A]): Future[Either[A, B]] =
  if (test) right.map(Right(_)) else left.map(Left(_))

  private def makeRequest[A <: AnyRef: Manifest, B: Manifest](m: A, uri: Uri): Future[Either[ErrorResponse, B]] =
    Marshal(m).to[RequestEntity]
      .map(entity => HttpRequest(uri = uri, method = HttpMethods.POST, entity = entity))
      .flatMap(http.singleRequest(_))
      .flatMap(resp =>
        flatEitherCond(
          test = resp.status == StatusCodes.OK,
          right = Unmarshal(resp.entity).to[B],
          left = Unmarshal(resp.entity).to[ErrorResponse]
        )
      )

  override def receive: Receive = {
    case m: Ping2 =>
      makeRequest[Ping2, Ping2Response](m, ping2Uri).pipeTo(sender())
    case m: Send =>
      makeRequest[Send, SendResponse](m, sendUri).pipeTo(sender())
  }
}

object MandrillAPIActor {
}