import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    `maven-publish`
}

group = "dolphins"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    api(project(":dolphins-core"))
    api("io.reactivex.rxjava2:rxjava:2.2.15")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "dolphins"
            artifactId = "rx-dolphins"
            version = "0.1.0"

            from(components["kotlin"])
        }
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}