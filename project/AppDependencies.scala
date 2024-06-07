import play.sbt.PlayImport.ws
import sbt._

object AppDependencies {

  private val playVersion = "play-30"
  private val bootstrapVersion = "8.4.0"

  val compile = Seq(
    ws,
    "uk.gov.hmrc"    %% s"domain-$playVersion"            % "9.0.0",
    "uk.gov.hmrc"    %% s"bootstrap-backend-$playVersion" % bootstrapVersion,
    "uk.gov.hmrc"    %% "stub-data-generator"             % "1.1.0",
    "com.github.fge" %  "json-schema-validator"           % "2.2.6",
    compilerPlugin("com.github.ghik" % "silencer-plugin" % "1.7.14" cross CrossVersion.full),
    "com.github.ghik" % "silencer-lib"                    % "1.7.14" % Provided cross CrossVersion.full
  )

  val test = Seq(
    "uk.gov.hmrc"            %% s"bootstrap-test-$playVersion" % bootstrapVersion,
    "org.scalatestplus.play" %% "scalatestplus-play"           % "7.0.0",
    "org.scalatestplus"      %% "scalacheck-1-17"              % "3.2.17.0"
  ).map(_ % "test")
}
