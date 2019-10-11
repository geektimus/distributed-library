// *****************************************************************************
// Project Definitions
// *****************************************************************************

lazy val `distributed-library` =
  project
    .in(file("."))
    .enablePlugins(AutomateHeaderPlugin, GitVersioning)
    .settings(name := "Distributed Library")
    .aggregate(core, http)

lazy val core =
  project
    .in(file("core"))
    .enablePlugins(AutomateHeaderPlugin, GitVersioning)
    .settings(name := "Distributed Library Core")
    .settings(settings)
    .settings(
      libraryDependencies ++= Seq(
          library.log4j2Api,
          library.log4j2Core,
          library.log4j2Scala,
          library.slf4j,
          library.scalaCheck % Test,
          library.specs2 % Test
        )
    )

lazy val http =
  project
    .in(file("http"))
    .enablePlugins(AutomateHeaderPlugin, GitVersioning)
    .settings(name := "Distributed Library HTTP")
    .settings(settings)
    .settings(
      libraryDependencies ++= Seq(
          library.log4j2Api,
          library.log4j2Core,
          library.log4j2Scala,
          library.slf4j,
          library.scalaCheck % Test,
          library.specs2 % Test
        )
    )

// *****************************************************************************
// Library dependencies
// *****************************************************************************

lazy val library =
  new {

    object Version {
      val log4j2       = "2.11.0"
      val log4j2Scala  = "11.0"
      val scalaLogging = "3.9.2"
      val slf4j        = "2.12.0"
      val scalaCheck   = "1.14.0"
      val specs2       = "4.4.1"
    }

    val log4j2Api   = "org.apache.logging.log4j" % "log4j-api" % Version.log4j2
    val log4j2Core  = "org.apache.logging.log4j" % "log4j-core" % Version.log4j2 % Runtime
    val log4j2Scala = "org.apache.logging.log4j" % "log4j-api-scala_2.12" % Version.log4j2Scala
    val slf4j       = "org.apache.logging.log4j" % "log4j-slf4j-impl" % Version.slf4j
    
    val scalaCheck  = "org.scalacheck" %% "scalacheck" % Version.scalaCheck
    val specs2      = "org.specs2" %% "specs2-core" % Version.specs2
  }

// *****************************************************************************
// Settings
// *****************************************************************************

lazy val settings =
  commonSettings ++
  gitSettings ++
  scalafmtSettings

lazy val commonSettings =
  Seq(
    // scalaVersion from .travis.yml via sbt-travisci
    // scalaVersion := "2.12.10",
    isTravisBuild := true,
    version := "0.0.1-SNAPSHOT",
    organization := "com.codingmaniacs.scala.projects",
    headerLicense := Some(HeaderLicense.MIT("2019", "Geektimus")),
    scalacOptions ++= Seq(
        "-deprecation",
        "-encoding",
        "UTF-8",
        "-feature",
        "-language:existentials",
        "-language:higherKinds",
        "-language:implicitConversions",
        "-language:postfixOps",
        "-target:jvm-1.8",
        "-unchecked",
        "-Xfatal-warnings",
        "-Xlint",
        "-Yno-adapted-args",
        "-Ypartial-unification",
        "-Ywarn-dead-code",
        "-Ywarn-infer-any",
        "-Ywarn-numeric-widen",
        "-Ywarn-unused",
        "-Ywarn-unused-import",
        "-Ywarn-value-discard"
      ),
    parallelExecution.in(Test) := false,
    unmanagedSourceDirectories.in(Compile) := Seq(scalaSource.in(Compile).value),
    unmanagedSourceDirectories.in(Test) := Seq(scalaSource.in(Test).value),
    fork in Test := true
  )

lazy val gitSettings =
  Seq(
    git.useGitDescribe := true
  )

lazy val scalafmtSettings =
  Seq(
    scalafmtOnCompile := true
  )
