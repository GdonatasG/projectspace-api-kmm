pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "projectspace-api"

include(":libraries:test")
include(":libraries:utils")
include(":libraries:coroutines")
include(":libraries:http")
include(":libraries:logger")
include(":projectspace-services")
