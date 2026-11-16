import play.sbt.PlayImport.ws
import sbt.*

object AppDependencies {

  private val playVersion = "play-30"
  private val bootstrapVersion = "10.9.0"

  val compile = Seq(
    ws,
    "uk.gov.hmrc"    %% s"domain-$playVersion"            % "13.0.0",
    "uk.gov.hmrc"    %% s"bootstrap-backend-$playVersion" % bootstrapVersion,
    "com.networknt" %  "json-schema-validator"           % "2.0.1" exclude ("com.fasterxml.jackson.core", "jackson-databind")
  )

  val test = Seq(
    "uk.gov.hmrc"            %% s"bootstrap-test-$playVersion" % bootstrapVersion,
    "uk.gov.hmrc"    %% "stub-data-generator"             % "1.6.0",
    "org.scalatestplus"      %% "scalacheck-1-17"              % "3.2.18.0"
  ).map(_ % "test")
}
