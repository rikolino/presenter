import sbt._
import Keys._
import scala.scalajs.sbtplugin.ScalaJSPlugin._
import ScalaJSKeys._

object Build extends sbt.Build {
  lazy val root = project.in(file(".")).settings(
    scalaJSSettings: _*
  ).settings(
    name := "presentation",
    scalaVersion := "2.11.1",
    libraryDependencies += "org.scala-lang.modules.scalajs" %%% "scalajs-dom" % "0.5",
    libraryDependencies += "org.scala-lang.modules.scalajs" %%% "scalajs-jquery" % "0.5",
    libraryDependencies += "org.scala-lang.modules.scalajs" %% "scalajs-jasmine-test-framework" % scalaJSVersion % "test"
  )
}