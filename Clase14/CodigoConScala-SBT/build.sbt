name := "EstudioVentasCosmeticos"

version := "0.1"

scalaVersion := "2.11.8"
//scalaVersion := "2.12.0"

val sparkVersion = "2.2.0"

libraryDependencies ++= Seq("org.apache.spark" %% "spark-core" % sparkVersion % "provided","org.apache.spark" %% "spark-sql" % sparkVersion % "provided")


        