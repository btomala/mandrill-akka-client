package mandrillclient.core

import com.typesafe.config.ConfigFactory

trait MandrillClientSettings { self: Settings =>

  private val mandrill = "mandrill"
  private val mandrillTemplates = mandrill + ".api.templates"
  private lazy val mandrillTest = mandrill + ".test"

  self.config.checkValid(ConfigFactory.defaultReference(), mandrill)

  val endpoint = self.config.getString(mandrill + ".endpoint")

  val key = self.config.getString(mandrill + ".key")
  val ping2 = self.config.getString(mandrill + ".api.users.ping2")
  val send = self.config.getString(mandrill + ".api.messages.send")
  val addTemplate = self.config.getString(mandrillTemplates + ".add")
  val infoTemplate = self.config.getString(mandrillTemplates + ".info")
  val updateTemplate = self.config.getString(mandrillTemplates + ".update")
  val deleteTemplate = self.config.getString(mandrillTemplates + ".delete")
  val listTemplate = self.config.getString(mandrillTemplates + ".list")

  lazy val testEmail = self.config.getString(mandrillTest + ".email")
  lazy val testKey = self.config.getString(mandrillTest + ".key")
  lazy val templatesClean = self.config.getBoolean(mandrillTest + ".templates.clean")
}