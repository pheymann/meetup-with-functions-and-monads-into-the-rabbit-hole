name          := "user-service"
version       := "0.1.0"
scalaVersion  := "2.12.2"
scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

mainClass in Compile := Some("Server")

libraryDependencies ++= {
  val akkaVersion = "2.5.4"

  Seq(
    "com.typesafe.akka" %% "akka-actor"  % akkaVersion % Compile,
    "com.typesafe.akka" %% "akka-stream" % akkaVersion % Compile,
    "com.typesafe.akka" %% "akka-http"   % "10.0.9" % Compile,

    "com.typesafe.play" %% "play-json" % "2.6.2" % Compile,
    "de.heikoseeberger" %% "akka-http-play-json" % "1.17.0" % Compile,

    "org.typelevel" %% "cats-core" % "0.9.0" % Compile,

    "org.rogach" %% "scallop" % "3.1.0" % Compile,

    "org.specs2" %% "specs2-core" % "3.9.4" % Test
  )
}

addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.3")

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "utf-8",
  "-explaintypes",
  "-feature", 
  "-unchecked",
  "-Xfatal-warnings",
  "-Xfuture",
  "-Xlint:adapted-args",
  "-Xlint:inaccessible",
  "-Xlint:infer-any",
  "-Xlint:missing-interpolator",
  "-Xlint:option-implicit",
  "-Xlint:type-parameter-shadow",
  "-Xlint:unsound-match",
  "-Ywarn-dead-code",
  "-Ywarn-inaccessible",
  "-Ywarn-infer-any",
  "-Ywarn-numeric-widen",
  "-Ywarn-unused:implicits",
  "-Ywarn-unused:imports",
  "-Ywarn-unused:locals",
  "-Ywarn-unused:params",
  "-Ywarn-unused:patvars",
  "-Ywarn-unused:privates"
)
