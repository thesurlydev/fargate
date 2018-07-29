import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.dsl.Coroutines
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformJvmPlugin
import org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("org.jetbrains.kotlin.jvm") version "1.2.51" apply false
  id("io.spring.dependency-management") version "1.0.6.RELEASE"
  id("java-library")
  id("maven-publish")
  id("com.google.cloud.tools.jib") version "0.9.6"
}

apply {
  plugin("org.jetbrains.kotlin.jvm")
  plugin("java-library")
  plugin("io.spring.dependency-management")
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    jvmTarget = "1.8"
    suppressWarnings = false
    freeCompilerArgs = listOf("-Xjsr305=strict", "-Xjvm-default=enable")
  }
}

repositories {
  jcenter()
  mavenLocal()
  mavenCentral()
  maven("https://dl.bintray.com/kotlin/kotlin-eap")
  maven("https://kotlin.bintray.com/kotlinx")
}

dependencyManagement {
  val awsVersion: String by project
  val jacksonVersion: String by project
  val slf4jVersion: String by project

  imports {
    mavenBom("com.amazonaws:aws-java-sdk-bom:$awsVersion")
    mavenBom("com.fasterxml.jackson:jackson-bom:$jacksonVersion")
    mavenBom("org.slf4j:slf4j-parent:$slf4jVersion")
  }

  val junitVersion: String by project
  val mockitoKotlinVersion: String by project

  dependencies {
    dependency("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    dependency("org.junit.jupiter:junit-jupiter-params:$junitVersion")
    dependency("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
    dependency("com.nhaarman.mockitokotlin2:mockito-kotlin:$mockitoKotlinVersion")
    dependency("org.slf4j:slf4j-api:$slf4jVersion")
    dependency("org.slf4j:slf4j-log4j12:$slf4jVersion")
  }
}

tasks.withType<Test> {
  //systemProperty("AWS_PROFILE", System.getenv("AWS_PROFILE"))
  useJUnitPlatform {
    val includeTag: String = when {
      project.hasProperty("runIntegrationTests") -> "slow"
      else -> "fast"
    }
    includeTags(includeTag)
  }
  testLogging {
    events("passed", "skipped", "failed")
  }
}


dependencies {
  val sparkKotlinVersion: String by project
  val sparkVersion: String by project

  api("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
  compile("com.fasterxml.jackson.module:jackson-module-kotlin")
  compile("com.sparkjava:spark-kotlin:$sparkKotlinVersion")
  compile("com.sparkjava:spark-template-mustache:$sparkVersion")
  compile("org.slf4j:slf4j-log4j12")
}

jib {
  to {
    image = "${project.name}:${project.version}"
  }
}