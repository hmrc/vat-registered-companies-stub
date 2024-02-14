import play.sbt.PlayImport.ws
import sbt._

object AppDependencies {

  private val playSuffix = "-play-30"
  private val bootstrapVersion = "8.4.0"

  val compile = Seq(
    ws,
    "com.github.fge" %  "json-schema-validator"         % "2.2.6",
    "uk.gov.hmrc"    %% s"domain$playSuffix"            % "9.0.0",
    "uk.gov.hmrc"    %% s"bootstrap-backend$playSuffix" % bootstrapVersion,
    "uk.gov.hmrc"    %% "stub-data-generator"           % "1.1.0"
  )

  val test = Seq(
    "org.scalatestplus"      %% "scalacheck-1-17"            % "3.2.17.0",
    "org.scalatestplus.play" %% "scalatestplus-play"         % "7.0.0",
    "uk.gov.hmrc"            %% s"bootstrap-test$playSuffix" % bootstrapVersion
  ).map(_ % "test")
}
