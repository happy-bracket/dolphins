import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.60"
}

group = "ru.hbracket"
version = "0.1.0"

val arrowVersion = "0.10.3"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("io.reactivex.rxjava3:rxjava:3.0.0-RC6")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}