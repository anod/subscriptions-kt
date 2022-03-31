
plugins {
    id("com.android.library")
    id("kotlin-multiplatform")
    id("com.squareup.sqldelight")
}

sqldelight {
    database("SubscriptionsDatabase") {
        packageName = "info.anodsplace.subscriptions.database"
    }
}

android {
    compileSdk = 31

    defaultConfig {
        minSdk = 23
        targetSdk = 31
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    sourceSets {
        named("main") {
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
            res.srcDirs("src/androidMain/res")
        }
    }
}

kotlin {
    android()

    js(IR) {
        browser()
    }

    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(project(":common:server-contract"))
                implementation(libs.coroutines.core)
                implementation(libs.koin.core)
                implementation(libs.ktor.client.core)
                implementation(libs.sqldelight.coroutines)
            }
        }

        named("androidMain") {
            dependencies {
                implementation(libs.sqldelight.android)
                implementation(libs.sqldelight.sqlite)
            }
        }

        named("jsMain") {
            dependencies {
                implementation(libs.sqldelight.sqljs)
            }
        }

        named("commonTest") {
            dependencies {
                implementation(libs.kotlin.test.common)
                implementation(libs.kotlin.test.annotations)
            }
        }

        named("androidTest") {
            dependencies {
                implementation(libs.kotlin.test.junit)
            }
        }

        named("jsTest") {
            dependencies {
                implementation(libs.kotlin.test.js)
            }
        }
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }
}
