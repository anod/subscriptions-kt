
plugins {
    id("com.android.library")
    id("kotlin-multiplatform")
    id("com.apollographql.apollo3").version("3.2.0")
}

apollo {
    packageName.set("info.anodsplace.subscriptions.graphql")
    mapScalarToKotlinString("uuid")
    mapScalarToKotlinString("date")
    mapScalarToKotlinFloat("numeric")
}

android {
    compileSdk = 31

    defaultConfig {
        minSdk = 29
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
    jvm("desktop")
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
                implementation(libs.apollo.graphql)
                implementation(libs.apollo.normalized.cache)
            }
        }

        named("androidMain") {
            dependencies {
            }
        }

        named("desktopMain") {
            dependencies {
            }
        }

        named("jsMain") {
            dependencies {
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
