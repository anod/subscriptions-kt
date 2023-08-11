
plugins {
    id("com.android.library")
    kotlin("multiplatform")
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

kotlin {
    jvm("desktop")

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":common:server-contract"))
                implementation(libs.coroutines.core)
                implementation(libs.koin.core)
                implementation(libs.ktor.client.core)
                implementation(libs.apollo.graphql)
                implementation(libs.apollo.normalized.cache)
            }
        }
        val desktopMain by getting {
            kotlin.srcDir("build/generated/ksp/desktop/desktopMain/kotlin")
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
    arg("info.anodsplace.dotenv.allowedKeys", "*ENDPOINT;SBS_ENV")
    arg("info.anodsplace.dotenv.camelCase", "true")
}