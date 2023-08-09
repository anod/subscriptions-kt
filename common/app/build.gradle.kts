
plugins {
    id("com.android.library")
    id("kotlin-multiplatform")
    id("com.apollographql.apollo3").version("3.8.2")
    id("com.google.devtools.ksp")
}

apollo {
    service("service") {
        packageName.set("info.anodsplace.subscriptions.graphql")
        codegenModels.set("responseBased")
        mapScalarToKotlinString("uuid")
        mapScalarToKotlinString("date")
        mapScalarToKotlinFloat("numeric")
    }
}

android {
    compileSdk = 33

    defaultConfig {
        minSdk = 29
        targetSdk = 33
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

        named("androidUnitTest") {
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


dependencies {
    add("kspCommonMainMetadata", project(":ksp-dotenv"))
    add("kspDesktop", project(":ksp-dotenv"))
    add("kspJs", project(":ksp-dotenv"))
    add("kspAndroid", project(":ksp-dotenv"))

}

ksp {
    arg("info.anodsplace.dotenv.path", project.rootDir.toString())
    arg("info.anodsplace.dotenv.includeKeys", "*ENDPOINT;SBS_SERVER_PORT;HASURA_PORT")
}