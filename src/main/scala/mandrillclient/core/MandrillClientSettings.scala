package mandrillclient.core

import com.typesafe.config.ConfigFactory

trait MandrillClientSettings { self: Settings =>

  self.config.checkValid(ConfigFactory.defaultReference(),"mandrill")
  val endpoint = self.config.getString("mandrill.endpoint")
  val key = self.config.getString("mandrill.key")

}