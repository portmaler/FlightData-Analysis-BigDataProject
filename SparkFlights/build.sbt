ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.13"

lazy val root = (project in file("."))
  .settings(
    name := "SparkFlights",
    idePackagePrefix := Some("com.diremund.scalaapp")
  )

libraryDependencies += "org.apache.spark" %% "spark-streaming-kafka-0-10" % "3.1.2"
libraryDependencies += "org.apache.spark" %% "spark-sql" % "3.1.2" // % "provided"
libraryDependencies += "org.apache.spark" %% "spark-sql-kafka-0-10" % "3.1.2"
libraryDependencies += "org.apache.spark" %% "spark-core" % "3.1.2"
