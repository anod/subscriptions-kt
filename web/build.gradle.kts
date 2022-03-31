import org.jetbrains.compose.compose

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    kotlin("plugin.serialization")
}

kotlin {
    js(IR) {
        browser {
        }
        binaries.executable()
    }

    sourceSets {
        named("jsMain") {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.web.core)
                implementation(project(":common:app"))
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.logging)
                implementation(libs.ktor.client.js)
                implementation(libs.ktor.client.serialization)
                implementation(libs.koin.core)
                implementation(npm("graphql", "15.7.2"))
                implementation(npm("@apollo/client", "3.4.17"))
                implementation(npm("copy-webpack-plugin", "9.0.0"))
            }
        }
    }
}

val browserDist: Configuration by configurations.creating {
    isCanBeConsumed = true
    isCanBeResolved = false
}

artifacts {
    add(browserDist.name, tasks.named("jsBrowserDistribution").map { it.outputs.files.files.single() })
}

fun isDevEnv(): Boolean = env.SBS_ENV.orNull() == "dev"

rootProject.plugins.withType(org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin::class.java) {
    rootProject.the<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension>().versions.webpackCli.version = "4.9.0"
}