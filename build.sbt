import play.PlayImport._

name := """one-play-two-springs"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

val javaVersion = "1.8"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  javaJpa.exclude("org.hibernate.javax.persistence", "hibernate-jpa-2.0-api"),
  "org.hibernate" % "hibernate-entitymanager" % "4.3.9.Final",
  "org.springframework" % "spring-context" % "4.1.6.RELEASE",
  "org.springframework.data" % "spring-data-jpa" % "1.9.2.RELEASE",
  "org.projectlombok" % "lombok" % "1.16.6"
)
