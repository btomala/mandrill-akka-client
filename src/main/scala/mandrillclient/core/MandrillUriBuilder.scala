package mandrillclient.core

import akka.http.scaladsl.model.Uri

class MandrillUriBuilder(settings: MandrillClientSettings) {
  private def buildAddress(path: String): Uri = Uri(settings.endpoint + path)
  val ping2 = buildAddress(settings.ping2)
  val send = buildAddress(settings.send)
  val sendTemplate = buildAddress(settings.sendTemplate)
  val addTemplate = buildAddress(settings.addTemplate)
  val updateTemplate = buildAddress(settings.updateTemplate)
  val deleteTemplate = buildAddress(settings.deleteTemplate)
  val listTemplate = buildAddress(settings.listTemplate)
}
