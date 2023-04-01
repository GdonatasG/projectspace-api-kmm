object Versions {
    const val kotlin = "1.8.0"
    const val coroutines = "1.6.4"
    const val ktor = "2.1.3"
    const val kotlinxSerialization = "1.3.3"
    const val gradle = "7.4.2"
    const val ksp = "1.8.0-1.0.9"
    const val napier = "2.6.1"

    const val androidMinSdk = 26
    const val androidCompileSdk = 33
    const val androidTargetSdk = androidCompileSdk

    const val koinCore = "3.2.2"
    const val koinAndroid = "3.3.1"
    const val koinAndroidCompose = "3.4.0"

    const val multiplatformSettings = "0.9"

    const val compose = "1.4.0-alpha03"
    const val composeCompiler = "1.4.1"
    const val composeMaterial3 = "1.0.0"
    const val navCompose = "2.5.2"
    const val composeDestinations = "1.7.27-beta"
    const val accompanist = "0.27.0"
}

object Dependencies {
    object Gradle {
        const val plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
        const val gradle = "com.android.tools.build:gradle:${Versions.gradle}"
    }

    object Koin {
        const val core = "io.insert-koin:koin-core:${Versions.koinCore}"
        const val test = "io.insert-koin:koin-test:${Versions.koinCore}"
        const val testJUnit4 = "io.insert-koin:koin-test-junit4:${Versions.koinCore}"
        const val android = "io.insert-koin:koin-android:${Versions.koinAndroid}"
        const val compose = "io.insert-koin:koin-androidx-compose:${Versions.koinAndroidCompose}"
    }

    object Android {
        const val coreKtx = "androidx.core:core-ktx:1.9.0"
        const val appCompat = "androidx.appcompat:appcompat:1.6.0"

        object Accompanist {
            const val flowLayout = "com.google.accompanist:accompanist-flowlayout:${Versions.accompanist}"
        }
    }

    object Compose {
        const val material3 = "androidx.compose.material3:material3:${Versions.composeMaterial3}"
        const val material = "androidx.compose.material:material:1.3.1"
        const val materialIcons = "androidx.compose.material:material-icons-extended:1.3.1"
        const val activity = "androidx.activity:activity-compose:1.6.1"
        const val runtimeLivedata = "androidx.compose.runtime:runtime-livedata:1.3.3"
        const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1"

        const val navigation = "androidx.navigation:navigation-compose:${Versions.navCompose}"
    }

    object ComposeDestinations {
        const val core = "io.github.raamcosta.compose-destinations:core:${Versions.composeDestinations}"
        const val animations =
            "io.github.raamcosta.compose-destinations:animations-core:${Versions.composeDestinations}"
        const val ksp = "io.github.raamcosta.compose-destinations:ksp:${Versions.composeDestinations}"
    }

    object Ktor {
        const val clientCore = "io.ktor:ktor-client-core:${Versions.ktor}"
        const val clientIos = "io.ktor:ktor-client-ios:${Versions.ktor}"
        const val clientDarwin = "io.ktor:ktor-client-darwin:${Versions.ktor}"
        const val clientCIO = "io.ktor:ktor-client-cio:${Versions.ktor}"
        const val clientMock = "io.ktor:ktor-client-mock:${Versions.ktor}"
        const val contentNegotiation = "io.ktor:ktor-client-content-negotiation:${Versions.ktor}"
        const val serialization = "io.ktor:ktor-serialization-kotlinx-json:${Versions.ktor}"
    }

    object KotlinX {
        const val serializationCore =
            "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.kotlinxSerialization}"
        const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
        const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
    }

    object MultiplatformSettings {
        const val preferences = "com.russhwolf:multiplatform-settings:${Versions.multiplatformSettings}"
        const val preferencesNoArgs = "com.russhwolf:multiplatform-settings-no-arg:${Versions.multiplatformSettings}"
        const val preferencesMock = "com.russhwolf:multiplatform-settings-test:${Versions.multiplatformSettings}"
    }

    object Logging {
        const val napier = "io.github.aakira:napier:${Versions.napier}"
    }
}
