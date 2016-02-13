package mandrillclient.core

import com.typesafe.config.ConfigFactory

trait MandrillClientSettings { self: Settings =>

  private val mandrill = "mandrill"
  private val mandrillTemplates = mandrill + ".api.templates"

  self.config.checkValid(ConfigFactory.defaultReference(), mandrill)

  val endpoint = self.config.getString(mandrill + ".endpoint")

  val key = self.config.getString(mandrill + ".key")
  val ping2 = self.config.getString(mandrill + ".api.users.ping2")
  val send = self.config.getString(mandrill + ".api.messages.send")
  val addTemplate = self.config.getString(mandrillTemplates + ".add")
  val deleteTemplate = self.config.getString(mandrillTemplates + ".delete")
  val updateTemplate = self.config.getString(mandrillTemplates + ".update")
  val infoTemplate = self.config.getString(mandrillTemplates + ".info")

  lazy val testEmail = self.config.getString(mandrill + ".test.email")
  lazy val testKey = self.config.getString(mandrill + ".test.key")
}