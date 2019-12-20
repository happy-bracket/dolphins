
group = "dolphins"
version = "0.1.0"

buildscript {
    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.60")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }

    // workaround for https://youtrack.jetbrains.com/issue/KT-27170
    configurations.create("compileClasspath")
}