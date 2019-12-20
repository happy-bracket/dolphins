plugins {
    kotlin("multiplatform")
}

repositories {
    mavenCentral()
}

kotlin {

    jvm()
    js()
    iosArm32(); iosArm64(); iosX64()
    
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
    }
}