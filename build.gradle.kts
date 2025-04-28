import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("org.jmailen.kotlinter")
    id("io.ktor.plugin") version "3.1.2"
    application
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_21)
    }
}

application {
    mainClass = "no.nav.helsearbeidsgiver.AppKt"
}

group = "no.nav.helsearbeidsgiver"

version = "1.0.0"

repositories {
    val githubPassword: String by project

    mavenCentral()
    maven {
        setUrl("https://maven.pkg.github.com/navikt/*")
        credentials {
            username = "x-access-token"
            password = githubPassword
        }
    }
}

dependencies {
    val utilsVersion: String by project
    val kotestVersion: String by project
    val unleashVersion: String by project
    val kafkaVersion: String by project
    val logbackVersion: String by project
    val logbackEncoderVersion: String by project

    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("net.logstash.logback:logstash-logback-encoder:$logbackEncoderVersion")
    implementation("no.nav.helsearbeidsgiver:utils:$utilsVersion")
    implementation("io.getunleash:unleash-client-java:$unleashVersion")
    implementation("org.apache.kafka:kafka-clients:$kafkaVersion")
    implementation("io.ktor:ktor-server-core")
    implementation("io.ktor:ktor-server-netty-jvm")

    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
}

tasks.test {
    useJUnitPlatform()
}
