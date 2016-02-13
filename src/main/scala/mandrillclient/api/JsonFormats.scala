package mandrillclient.api

import java.text.SimpleDateFormat

import mandrillclient.api.constants._
import org.json4s.ext.{JodaTimeSerializers, EnumNameSerializer}
import org.json4s.{DefaultFormats, Formats}

object JsonFormats {
  val dateFormats = new DefaultFormats {
    override def dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSS")
  }
  val formats: Formats = dateFormats ++
                         JodaTimeSerializers.all +
                     new EnumNameSerializer(ErrorName) +
                     new EnumNameSerializer(SendToType) +
                     new EnumNameSerializer(SendStatus) +
                     new EnumNameSerializer(MargeLanguage) +
                     new EnumNameSerializer(RejectReason)
}
