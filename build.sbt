name := "programming-pearls-scala"

version := "0.1"

scalaVersion := "2.13.4"

resolvers += "Sonatype OSS Snapshots" at
  "https://oss.sonatype.org/content/repositories/releases"

libraryDependencies += "org.specs2" %% "specs2-core" % "4.10.0" % "test"
libraryDependencies += "com.storm-enroute" %% "scalameter" % "0.19" % "test"

testFrameworks += new TestFramework("org.scalameter.ScalaMeterFramework")

parallelExecution in Test := false

scalacOptions in Test ++= Seq("-Yrangepos")
