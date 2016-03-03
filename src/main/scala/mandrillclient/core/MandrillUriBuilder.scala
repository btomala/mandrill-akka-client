package mandrillclient.core

import akka.http.scaladsl.model.Uri

class MandrillUriBuilder(settings: MandrillClientSettings) {
  private def buildAddress(path: String): Uri = Uri(settings.endpoint + path)

  lazy val ping2 = buildAddress(settings.ping2)

  lazy val send = buildAddress(settings.send)
  lazy val sendTemplate = buildAddress(settings.sendTemplate)
  lazy val content = buildAddress(settings.content)

  lazy val addTemplate = buildAddress(settings.addTemplate)
  lazy val updateTemplate = buildAddress(settings.updateTemplate)
  lazy val deleteTemplate = buildAddress(settings.deleteTemplate)
  lazy val listTemplate = buildAddress(settings.listTemplate)

  lazy val addWebhook = buildAddress(settings.addWebhook)
  lazy val infoWebhook = buildAddress(settings.infoWebhook)
  lazy val deleteWebhook = buildAddress(settings.deleteWebhook)
  lazy val listWebhook = buildAddress(settings.listWebhook)
}
