package mandrillclient.core

import akka.actor.{ActorSystem, Actor}
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.pattern.PipeToSupport
import akka.stream.ActorMaterializer
import mandrillclient.api.{ErrorName, ErrorResponse, MandrillRequest}
import mandrillclient.api.Users.{Ping2, Ping2Response}
import mandrillclient.core.MandrillAPIActor.Ping2
import de.heikoseeberger.akkahttpjson4s.Json4sSupport._
import org.json4s.{native, DefaultFormats}

import scala.concurrent.Future
import scala.util.{Left, Right, Either}
import org.json4s.ext.EnumNameSerializer

class MandrillAPIActor(settings: MandrillClientSettings)(implicit system: ActorSystem) extends Actor with PipeToSupport {

  implicit val materializer = ActorMaterializer()
  implicit val formats = DefaultFormats +  new EnumNameSerializer(ErrorName)
  implicit val serialization = native.Serialization

  import system.dispatcher
  private val http = Http()
  private val logger = Logging(context.system, this)
  private val ping2Uri = Uri(settings.endpoint + settings.ping2)

  private def flatEitherCond[A, B](test: Boolean, right: => Future[B], left: => Future[A]): Future[Either[A, B]] =
  if (test) right.map(Right(_)) else left.map(Left(_))

  override def receive: Receive = {
    case m: Ping2 =>
      Marshal(m).to[RequestEntity]
                .map(entity => HttpRequest(uri = ping2Uri, method = HttpMethods.POST, entity = entity))
                .flatMap(http.singleRequest(_))
                .flatMap(resp =>
                  flatEitherCond(
                    test = resp.status == StatusCodes.OK,
                    right = Unmarshal(resp.entity).to[Ping2Response],
                    left = Unmarshal(resp.entity).to[ErrorResponse]
                  )
                )
                .pipeTo(sender())
  }
}

object MandrillAPIActor {
}