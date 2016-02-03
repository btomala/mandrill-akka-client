package mandrillclient.core

import com.typesafe.config.ConfigFactory

trait MandrillClientSettings { self: Settings =>

  private val mandrill = "mandrill"

  self.config.checkValid(ConfigFactory.defaultReference(), mandrill)

  val endpoint = self.config.getString(mandrill + ".endpoint")
  val key = self.config.getString(mandrill + ".key")

  val ping2 = self.config.getString(mandrill + ".api.users.ping2")
}