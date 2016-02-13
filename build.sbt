name := "mandrill-akka-client"

scalaVersion := "2.11.7"

organization := "btomala"

licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0"))

resolvers += Resolver.bintrayRepo("hseeberger", "maven")

lazy val `mandrill-akka-client` = (project in file(".")).enablePlugins(GitVersioning)

val json4sV = "3.3.0"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http-experimental"   % "2.0.3",
  "de.heikoseeberger" %% "akka-http-json4s"         % "1.4.2",
  "org.json4s"        %% "json4s-native"            % json4sV,
  "org.json4s"        %% "json4s-ext"               % json4sV
) ++ Seq(
  "org.scalatest"     %% "scalatest"                % "2.2.6"  % "test",
  "com.typesafe.akka" %% "akka-testkit"             % "2.3.14" % "test"
)