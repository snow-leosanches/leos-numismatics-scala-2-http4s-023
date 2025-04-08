val Http4sVersion = "0.23.30"
val CirceVersion = "0.14.12"
val MunitVersion = "1.1.0"
val LogbackVersion = "1.5.18"
val MunitCatsEffectVersion = "2.0.0"

lazy val root = (project in file("."))
  .settings(
    organization := "com.leosnumismatics",
    name := "leos-numismatics-scala-2-http4s-023",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.13.16",
    libraryDependencies ++= Seq(
      "org.http4s"      %% "http4s-ember-server" % Http4sVersion,
      "org.http4s"      %% "http4s-ember-client" % Http4sVersion,
      "org.http4s"      %% "http4s-circe"        % Http4sVersion,
      "org.http4s"      %% "http4s-dsl"          % Http4sVersion,
      "io.circe"        %% "circe-generic"       % CirceVersion,
      "org.scalameta"   %% "munit"               % MunitVersion           % Test,
      "org.typelevel"   %% "munit-cats-effect"   % MunitCatsEffectVersion % Test,
      "ch.qos.logback"  %  "logback-classic"     % LogbackVersion         % Runtime,
    ),
    addCompilerPlugin("org.typelevel" %% "kind-projector"     % "0.13.3" cross CrossVersion.full),
    addCompilerPlugin("com.olegpy"    %% "better-monadic-for" % "0.3.1"),
    assembly / assemblyMergeStrategy := {
      case "module-info.class" => MergeStrategy.discard
      case x => (assembly / assemblyMergeStrategy).value.apply(x)
    }
  )

libraryDependencies += "com.snowplowanalytics" %% "snowplow-scala-tracker-core" % "2.0.0"

// If you plan to use the http4s emitter with an Ember client
libraryDependencies += "com.snowplowanalytics" %% "snowplow-scala-tracker-emitter-http4s" % "2.0.0"
libraryDependencies += "org.http4s" %% "http4s-ember-client" % "0.23.30"