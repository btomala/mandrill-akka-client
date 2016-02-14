package mandrillclient.core

import akka.actor.{Actor, ActorSystem}
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.pattern.PipeToSupport
import akka.stream.ActorMaterializer
import de.heikoseeberger.akkahttpjson4s.Json4sSupport._
import mandrillclient.api.ErrorResponse
import mandrillclient.api.Messages.{SendTemplate, Send, SendResponse}
import mandrillclient.api.Templates._
import mandrillclient.api.Users.{Ping2, Ping2Response}
import org.json4s.{Formats, native}

import scala.concurrent.Future
import scala.util.{Either, Left, Right}

class MandrillAPIActor(settings: MandrillClientSettings)(implicit system: ActorSystem, formats: Formats) extends Actor with PipeToSupport {

  implicit val materializer = ActorMaterializer()
  implicit val serialization = native.Serialization

  import system.dispatcher //toDo check this out, blocking whole actor ore not?

  private val http = Http()
  private val logger = Logging(context.system, this)
  private val uri = new MandrillUriBuilder(settings)

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
      makeRequest[Ping2, Ping2Response](m, uri.ping2).pipeTo(sender())
    case m: Send =>
      makeRequest[Send, SendResponse](m, uri.send).pipeTo(sender())
    case m: SendTemplate =>
      makeRequest[SendTemplate, SendResponse](m, uri.sendTemplate).pipeTo(sender())
    case m: AddTemplate =>
      makeRequest[AddTemplate, TemplateResponse](m, uri.addTemplate).pipeTo(sender())
    case m: Info =>
      makeRequest[Info, TemplateResponse](m, uri.deleteTemplate).pipeTo(sender())
    case m: UpdateTemplate =>
      makeRequest[UpdateTemplate, TemplateResponse](m, uri.updateTemplate).pipeTo(sender())
    case m: Delete =>
      makeRequest[Delete, TemplateResponse](m, uri.deleteTemplate).pipeTo(sender())
    case m: List =>
      makeRequest[List, Seq[TemplateResponse]](m, uri.listTemplate).pipeTo(sender())
  }
}

object MandrillAPIActor {
//  def props() = Props(classOf[MandrillAPIActor])
}