
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        mavenLocal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven {
            val GITHUB_TOKEN: String by settings
            url = uri("https://maven.pkg.github.com/anod/ksp-dotenv")
            name = "ksp-dotenv"
            credentials {
                username = "anod"
                password = GITHUB_TOKEN
            }
        }
    }
    versionCatalogs {
        create("libs") {
            version("ktor", "2.3.4")
            library("ktor-client-core", "io.ktor", "ktor-client-core").versionRef("ktor")
            library("ktor-client-logging", "io.ktor", "ktor-client-logging").versionRef("ktor")
            library("ktor-client-js", "io.ktor", "ktor-client-js").versionRef("ktor")
            library("ktor-client-cio", "io.ktor", "ktor-client-cio").versionRef("ktor")
            library("ktor-client-content-negotiation", "io.ktor", "ktor-client-content-negotiation").versionRef("ktor")
            library("ktor-client-serialization", "io.ktor", "ktor-serialization-kotlinx-json").versionRef("ktor")

            library("ktor-server-core", "io.ktor", "ktor-server-core").versionRef("ktor")
            library("ktor-server-netty", "io.ktor", "ktor-server-netty").versionRef("ktor")
            library("ktor-server-html", "io.ktor", "ktor-server-html-builder").versionRef("ktor")
            library("ktor-server-auth", "io.ktor", "ktor-server-auth").versionRef("ktor")
            library("ktor-server-jwt", "io.ktor", "ktor-server-auth-jwt").versionRef("ktor")
            library("ktor-server-content-negotiation", "io.ktor", "ktor-server-content-negotiation").versionRef("ktor")
            library("ktor-server-serialization", "io.ktor", "ktor-serialization-kotlinx-json").versionRef("ktor")
            library("ktor-server-compression", "io.ktor", "ktor-server-compression").versionRef("ktor")
            library("ktor-server-cors", "io.ktor", "ktor-server-cors").versionRef("ktor")

            library("koin-core", "io.insert-koin:koin-core:3.5.0")

            version("kotlin", "1.9.10")
            library("kotlin-gradle-plugin", "org.jetbrains.kotlin", "kotlin-gradle-plugin").versionRef("kotlin")
            library("kotlin-test-common", "org.jetbrains.kotlin", "kotlin-test-common").versionRef("kotlin")
            library("kotlin-test-junit", "org.jetbrains.kotlin", "kotlin-test-junit").versionRef("kotlin")
            library("kotlin-test-js", "org.jetbrains.kotlin", "kotlin-test-js").versionRef("kotlin")
            library(
                "kotlin-test-annotations",
                "org.jetbrains.kotlin",
                "kotlin-test-annotations-common"
            ).versionRef("kotlin")
            library(
                "kotlin-serialization-gradle-plugin",
                "org.jetbrains.kotlin",
                "kotlin-serialization"
            ).versionRef("kotlin")

            version("coroutines", "1.7.3")
            library("coroutines-core", "org.jetbrains.kotlinx", "kotlinx-coroutines-core").versionRef("coroutines")
            library("coroutines-swing", "org.jetbrains.kotlinx", "kotlinx-coroutines-swing").versionRef("coroutines")
            library("kotlinx-html", "org.jetbrains.kotlinx:kotlinx-html-jvm:0.8.0")

            version("compose", "1.5.1")
            plugin("jetbrains-compose","org.jetbrains.compose").versionRef("compose")

            library("serialization-json", "org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")

            library("dotenv", "co.uzzu.dotenv:gradle:2.0.0")

            version("apollo", "3.8.2")
            library("apollo-graphql", "com.apollographql.apollo3", "apollo-runtime").versionRef("apollo")
            library("apollo-normalized-cache", "com.apollographql.apollo3", "apollo-normalized-cache").versionRef("apollo")
            plugin("apollo", "com.apollographql.apollo3").versionRef("apollo")

            library("ksp-dotenv", "info.anodsplace.dotenv", "ksp-dotenv-jvm").version("0.1.0")

            plugin("dotenv-gradle", "co.uzzu.dotenv.gradle").version("2.0.0")
        }
    }
}

pluginManagement {
    repositories {
        mavenLocal()
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven {
            val GITHUB_TOKEN: String by settings
            url = uri("https://maven.pkg.github.com/anod/ksp-dotenv")
            name = "ksp-dotenv"
            credentials {
                username = "anod"
                password = GITHUB_TOKEN
            }
        }
        gradlePluginPortal()
    }
}

include(
    ":common:app",
    ":common:server-contract",
    ":common:compose-ui",
    ":desktop",
    ":server",
)

rootProject.name = "subscriptions-kt"