plugins {
    kotlin("multiplatform")
    `maven-publish`
}

group = "dolphins"
version = "0.1.0"

repositories {
    mavenCentral()
}

kotlin {

    jvm {
        withJava()
    }
    /*
    js()
    iosArm32(); iosArm64(); iosX64()*/
    // commented out for now to not interfere with publishing process

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
            }
        }
    }
}