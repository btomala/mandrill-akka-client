package testutils

import mandrillclient.core.Settings

trait MandrillClientTestSettings { self: Settings =>

  private lazy val mandrillTest = "mandrill.test"

  lazy val testEmail = self.config.getString(mandrillTest + ".email")
  lazy val testKey = self.config.getString(mandrillTest + ".key")
  lazy val templatesClean = self.config.getBoolean(mandrillTest + ".templates.clean")
}