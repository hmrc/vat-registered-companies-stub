import play.sbt.PlayImport.ws
import sbt._

object AppDependencies {

  val compile = Seq(
    ws,
    "com.github.fge" % "json-schema-validator" % "2.2.6",
    "uk.gov.hmrc" %% "domain" % "8.3.0-play-28",
    "uk.gov.hmrc" %% "play-frontend-hmrc" % "7.7.0-play-28",
    "uk.gov.hmrc" %% "bootstrap-test-play-28" % "7.15.0",
    "uk.gov.hmrc" %% "stub-data-generator" % "1.1.0",
    "uk.gov.hmrc" %% "bootstrap-backend-play-28" % "7.15.0"
  )

  val test = Seq(
    "com.typesafe.play" %% "play-test" % "2.8.19",
    "org.scalatestplus" %% "mockito-3-4" % "3.2.10.0",
    "org.pegdown" % "pegdown" % "1.6.0",
    "org.scalatest" %% "scalatest" % "3.2.14",
    "org.scalacheck" %% "scalacheck" % "1.17.0",
    "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0",
    "uk.gov.hmrc" %% "bootstrap-test-play-28" % "7.15.0",
    "uk.gov.hmrc" %% "bootstrap-backend-play-28" % "7.15.0",
    "com.vladsch.flexmark" % "flexmark-all" % "0.62.2"
  ).map(_ % "test")
}
