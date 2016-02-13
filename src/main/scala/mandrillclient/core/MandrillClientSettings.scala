package mandrillclient.core

import com.typesafe.config.ConfigFactory

trait MandrillClientSettings { self: Settings =>

  private val mandrill = "mandrill"

  self.config.checkValid(ConfigFactory.defaultReference(), mandrill)

  val endpoint = self.config.getString(mandrill + ".endpoint")
  val key = self.config.getString(mandrill + ".key")

  val ping2 = self.config.getString(mandrill + ".api.users.ping2")
  val send = self.config.getString(mandrill + ".api.messages.send")
  val addTemplate = self.config.getString(mandrill + ".api.templates.add")
  val deleteTemplate = self.config.getString(mandrill + ".api.templates.delete")
  val updateTemplate = self.config.getString(mandrill + ".api.templates.update")

  lazy val testEmail = self.config.getString("test.email")
}