name := "mandrill-akka-client"

scalaVersion := "2.11.7"

organization := "btomala"

licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0"))

resolvers += "Bartek's repo at Bintray" at "https://dl.bintray.com/btomala/maven"
resolvers += Resolver.bintrayRepo("hseeberger", "maven")

lazy val `mandrill-akka-client` = (project in file(".")).enablePlugins(SbtTwirl, GitVersioning)

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http-experimental"          % "2.0.3",
  "com.typesafe.play" %% "twirl-api"                       % "1.1.1",
  "de.heikoseeberger" %% "akka-http-json4s"                % "1.4.2",
  "btomala"           %% "akka-http-twirl"                 % "1.1.0"
) ++ Seq(
  "org.scalatest"     %% "scalatest"                       % "2.2.6" % "test"
)