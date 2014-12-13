import sbt.Keys._
import sbt._

object build extends Build {

  lazy val core = Project(id = "core", base = file("smile-core"), settings = Settings.core)

  lazy val proj = Project(id = "projects", base = file("smile-projects"), settings = Settings.proj)

  object Settings {
    private val breezeV = "0.8.1"
    private val configV = "1.2.1"
    private val gsonV = "2.2.4"
    private val liblinearV = "1.94"
    private val openCsvV = "2.3"
    private val scalaCheckV = "1.11.4"
    private val scalaLoggingV = "2.1.2"
    private val scalaTestV = "2.2.0"
    private val scalazV = "7.1.0-RC1"
    private val sparkV = "1.0.0"
    private val playV = "2.3.1"

    def core = {
      Defaults.coreDefaultSettings ++
        net.virtualvoid.sbt.graph.Plugin.graphSettings ++
        Seq(
          name := "core",
          organization := "smile-ss",
          version := "0.0",
          scalaVersion := "2.10.4",
          scalacOptions := Seq(
            "-deprecation",
            "-encoding", "UTF-8",
            "-feature",
            "-unchecked"),
          resolvers ++= Seq(
            Classpaths.sbtPluginReleases,
            "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
            "Akka Repository" at "http://repo.akka.io/releases/",
            "Spray Repository" at "http://repo.spray.io/",
            "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/",
            "Sonatype OSS Releases" at "http://oss.sonatype.org/content/repositories/releases/",
            "opennlp sourceforge repo" at "http://opennlp.sourceforge.net/maven2"
          ),
          libraryDependencies ++= Seq(
            "org.scalatest" %% "scalatest" % scalaTestV % "test",
            "org.scalacheck" %% "scalacheck" % scalaCheckV % "test",
            "com.typesafe" % "config" % configV,
            "com.typesafe.play" %% "play-json" % playV,
            "com.typesafe.scala-logging" %% "scala-logging-slf4j" % scalaLoggingV,
            "net.sf.opencsv" % "opencsv" % openCsvV,
            "org.apache.spark" %% "spark-core" % sparkV
          )
        )
    }

    def proj = {
      core ++ Seq(
        name := "proj",
        libraryDependencies ++= Seq(
          "org.scalanlp" %% "breeze" % "0.8.1",
          "org.scalanlp" %% "breeze-natives" % "0.8.1",
          "org.scalanlp" %% "epic-parser-en-span" % "2014.6.3-SNAPSHOT",
          "com.chuusai" % "shapeless_2.10.4" % "2.0.0",
          "com.google.code.gson" % "gson" % gsonV,
          "de.bwaldvogel" % "liblinear" % liblinearV,
          "org.apache.spark" %% "spark-mllib" % sparkV,
          "org.apache.spark" %% "spark-sql" % sparkV,
          "org.apache.spark" %% "spark-graphx" % sparkV,
          "org.apache.spark" %% "spark-streaming" % sparkV,
          "org.scalaz" %% "scalaz-core" % scalazV
        )
      )
    }

  }


}