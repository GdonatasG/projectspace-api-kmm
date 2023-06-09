pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven {
            this.url = java.net.URI("https://repo1.maven.org/maven2/")
        }
    }
}

rootProject.name = "projectspace-api"

include(":android:androidApp")
include(":android:components")

include(":composition")

include(":libraries:test")
include(":libraries:utils")
include(":libraries:coroutines")
include(":libraries:http")
include(":libraries:logger")
include(":libraries:preferences")
include(":libraries:alerts")

include(":projectspace-services")
include(":projectspace-services:smoke-tests")

include(":feature:common")
include(":feature:common:android-ui")

include(":feature:splashscreen")
include(":feature:splashscreen:android-ui")

include(":feature:authorization")
include(":feature:authorization:android-ui")

include(":feature:projects")
include(":feature:projects:android-ui")

include(":feature:create-project")
include(":feature:create-project:android-ui")

include(":feature:profile")
include(":feature:profile:android-ui")

include(":feature:userinvitations")
include(":feature:userinvitations:android-ui")

include(":feature:edit-profile")
include(":feature:edit-profile:android-ui")

include(":feature:tasks")
include(":feature:tasks:android-ui")

include(":feature:createtask")
include(":feature:createtask:android-ui")

include(":feature:dashboard")
include(":feature:dashboard:android-ui")

include(":feature:inviteuser")
include(":feature:inviteuser:android-ui")
