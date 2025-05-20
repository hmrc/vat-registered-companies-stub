import AppDependencies.{compile => compileDependencies, test => testDependencies}
// ================================================================================
// Plugins
// ================================================================================
enablePlugins(
  play.sbt.PlayScala,
  SbtDistributablesPlugin
)

// ================================================================================
// Play configuration
// ================================================================================
PlayKeys.playDefaultPort := 8732

// ================================================================================
// Dependencies
// ================================================================================
scalaVersion := "3.3.4"

libraryDependencies ++= compileDependencies ++ testDependencies

libraryDependencySchemes += "org.scala-lang.modules" %% "scala-xml" % VersionScheme.Always

// ================================================================================
// Compiler flags
// ================================================================================

scalacOptions ++= Seq(
  "-deprecation",                      // Emit warning and location for usages of deprecated APIs.
  "-encoding", "utf-8",                // Specify character encoding used by source files.
  "-explaintypes",                     // Explain type errors in more detail.
  "-feature",                          // Emit warning and location for usages of features that should be imported explicitly.
  "-unchecked",                        // Enable additional warnings where generated code depends on assumptions.
  "-Xlint:private-shadow",             // A private field (or class parameter) shadows a superclass field.
  "-Xlint:type-parameter-shadow",      // A local type parameter shadows a type already in scope.
  "-Wconf:msg=Flag.*repeatedly:s",
  "-Wconf:msg=unused import*:s",
  "-Wconf:msg=unused explicit parameter*:s",
  "-Wconf:msg=unused private member*:s"

)

// ================================================================================
// Misc
// ================================================================================

majorVersion := 1
uk.gov.hmrc.sbtdistributables.SbtDistributablesPlugin.publishingSettings


