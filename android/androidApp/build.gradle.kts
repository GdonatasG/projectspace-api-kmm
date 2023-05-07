plugins {
    id("com.android.application")
    kotlin("android")
    id("com.google.devtools.ksp") version Versions.ksp
    kotlin("plugin.serialization") version Versions.kotlin
}

android {
    signingConfigs {
        create("release") {
            storeFile = file("projectspacetest.keystore")
            storePassword = "test123"
            keyAlias = "projectspace"
            keyPassword = "test123"
        }
    }
    namespace = "com.project.space"
    compileSdk = Versions.androidCompileSdk
    defaultConfig {
        applicationId = "com.project.space"
        minSdk = Versions.androidMinSdk
        targetSdk = Versions.androidTargetSdk
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.composeCompiler
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            this.signingConfig = signingConfigs.getByName("release")
        }
    }
    applicationVariants.all {
        kotlin.sourceSets {
            getByName(name) {
                kotlin.srcDir("build/generated/ksp/$name/kotlin")
            }
        }
    }
}

dependencies {
    implementation(project(":composition"))
    implementation(project(":android:components"))

    implementation(project(":feature:common"))
    implementation(project(":feature:common:android-ui"))

    implementation(project(":feature:splashscreen"))
    implementation(project(":feature:splashscreen:android-ui"))
    implementation(project(":feature:authorization:android-ui"))
    implementation(project(":feature:projects:android-ui"))
    implementation(project(":feature:create-project:android-ui"))
    implementation(project(":feature:profile:android-ui"))
    implementation(project(":feature:userinvitations:android-ui"))
    implementation(project(":feature:edit-profile:android-ui"))
    implementation(project(":feature:tasks:android-ui"))
    implementation(project(":feature:createtask:android-ui"))
    implementation(project(":feature:dashboard:android-ui"))

    ksp(Dependencies.Android.ComposeDestinations.ksp)
    implementation(Dependencies.KotlinX.serializationCore)

    implementation(Dependencies.Android.Accompanist.flowLayout)
    implementation(Dependencies.Android.Compose.material3)
    implementation(Dependencies.Android.Compose.materialIcons)
    implementation(Dependencies.Android.Compose.activity)
    implementation(Dependencies.Android.ComposeDestinations.core)
    implementation(Dependencies.Android.ComposeDestinations.animations)
    implementation(Dependencies.Android.Accompanist.systemUiController)
    implementation(Dependencies.Koin.core)
    implementation(Dependencies.Koin.android)
    implementation(Dependencies.Koin.compose)
    implementation(Dependencies.Android.splashScreen)

}
