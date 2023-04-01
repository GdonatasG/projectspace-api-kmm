plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

kotlin {
    android()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "http"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                with(Dependencies.Ktor) {
                    implementation(clientCore)
                    implementation(contentNegotiation)
                    implementation(serialization)
                }

                api(project(":libraries:logger"))
                implementation(project(":libraries:utils"))
            }
        }
        val commonTest by getting {
            dependencies {
                with(Dependencies.Ktor) {
                    implementation(clientMock)
                }

                implementation(kotlin("test"))
                implementation(project(":libraries:test"))
            }
        }
        val androidMain by getting {
            dependencies {
                with(Dependencies.Ktor) {
                    implementation(clientCIO)
                }
            }
        }
        val androidTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)

            dependencies {
                with(Dependencies.Ktor) {
                    implementation(clientDarwin)
                }
            }
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    namespace = "com.libraries.http"
    compileSdk = Versions.androidCompileSdk
    defaultConfig {
        minSdk = Versions.androidMinSdk
    }
}
