name := """scala_task_app"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

resolvers += Resolver.sonatypeRepo("snapshots")

scalaVersion := "2.11.12"

crossScalaVersions := Seq("2.11.12", "2.12.4")

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
libraryDependencies += jdbc
libraryDependencies += ws
libraryDependencies += "org.reactivemongo" %% "play2-reactivemongo" % "0.13.0-play26"

import play.sbt.routes.RoutesKeys
RoutesKeys.routesImport := Seq.empty
