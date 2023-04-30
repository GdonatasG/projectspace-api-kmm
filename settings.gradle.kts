pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "projectspace-api"

include(":android:androidApp")
include(":android:components")

include(":composition")

include(":feature:common")

include(":feature:splashscreen")
include(":feature:splashscreen:android-ui")

include(":feature:authorization")
include(":feature:authorization:android-ui")

include(":libraries:test")
include(":libraries:utils")
include(":libraries:coroutines")
include(":libraries:http")
include(":libraries:logger")
include(":libraries:preferences")
include(":libraries:alerts")

include(":projectspace-services")
include(":projectspace-services:smoke-tests")
