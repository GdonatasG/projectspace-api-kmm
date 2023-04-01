buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }

    dependencies {
        classpath(Dependencies.Gradle.plugin)
        classpath(Dependencies.Gradle.gradle)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

plugins {
    //trick: for the same plugin versions in all sub-modules
    id("com.android.library").version(Versions.gradle).apply(false)
    id("org.jetbrains.kotlin.android") version Versions.kotlin apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
