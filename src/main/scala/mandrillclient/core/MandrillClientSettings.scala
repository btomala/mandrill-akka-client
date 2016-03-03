package mandrillclient.core

import com.typesafe.config.ConfigFactory

trait MandrillClientSettings { self: Settings =>

  private val mandrill = "mandrill"
  private val mandrillTemplates = mandrill + ".api.templates"
  private val mandrillMessages = mandrill + ".api.messages"
  private val mandrillWebhook = mandrill + ".api.webhooks"

  self.config.checkValid(ConfigFactory.defaultReference(), mandrill)

  val endpoint = self.config.getString(mandrill + ".endpoint")

  val key = self.config.getString(mandrill + ".key")

  val ping2 = self.config.getString(mandrill + ".api.users.ping2")

  val send = self.config.getString(mandrillMessages + ".send")
  val sendTemplate = self.config.getString(mandrillMessages+ ".send-template")
  val content = self.config.getString(mandrillMessages+ ".content")

  val addTemplate = self.config.getString(mandrillTemplates + ".add")
  val infoTemplate = self.config.getString(mandrillTemplates + ".info")
  val updateTemplate = self.config.getString(mandrillTemplates + ".update")
  val deleteTemplate = self.config.getString(mandrillTemplates + ".delete")
  val listTemplate = self.config.getString(mandrillTemplates + ".list")

  val addWebhook = self.config.getString(mandrillWebhook + ".add")
  val infoWebhook = self.config.getString(mandrillWebhook + ".info")
  val deleteWebhook = self.config.getString(mandrillWebhook + ".delete")
  val listWebhook = self.config.getString(mandrillWebhook + ".list")
}