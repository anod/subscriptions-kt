
plugins {
    kotlin("multiplatform")
    alias(libs.plugins.apollo)
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
        val desktopMain by getting
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }
}

dependencies {
    add("kspCommonMainMetadata", libs.ksp.dotenv)
    add("kspDesktop", libs.ksp.dotenv)
}

ksp {
    arg("info.anodsplace.dotenv.path", project.rootDir.toString())
    arg("info.anodsplace.dotenv.allowedKeys", "*ENDPOINT;SBS_ENV")
    arg("info.anodsplace.dotenv.camelCase", "true")
    arg("info.anodsplace.dotenv.package", "info.anodsplace.subscriptions")
    arg("info.anodsplace.dotenv.class", "DotEnvClient")
}