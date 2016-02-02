package mandrillclient.core

import com.typesafe.config.ConfigFactory

class Settings {
  protected val config = ConfigFactory.load()
}