import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
}

group = "dolphins"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(project(":dolphins-core"))
    implementation("io.reactivex.rxjava2:rxjava:2.2.15")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}