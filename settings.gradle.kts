pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "projectspace-api"

include(":feature:common")
include(":feature:authorization")

include(":libraries:test")
include(":libraries:utils")
include(":libraries:coroutines")
include(":libraries:http")
include(":libraries:logger")
include(":libraries:preferences")

include(":projectspace-services")
include(":projectspace-services:smoke-tests")
include(":composition")
