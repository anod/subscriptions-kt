
dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            library("androidx-appcompat", "androidx.appcompat:appcompat:1.4.2")
            library("androidx-activity-compose", "androidx.activity:activity-compose:1.4.0")
            library("androidx-core", "androidx.core:core-ktx:1.7.0")
            library("android-gradle-plugin", "com.android.tools.build:gradle:7.0.4")

            version("ktor", "2.0.2")
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

            library("koin-core", "io.insert-koin:koin-core:3.2.0")

            version("kotlin", "1.6.21")
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

            version("coroutines", "1.6.2")
            library("coroutines-core", "org.jetbrains.kotlinx", "kotlinx-coroutines-core").versionRef("coroutines")
            library("coroutines-swing", "org.jetbrains.kotlinx", "kotlinx-coroutines-swing").versionRef("coroutines")
            library("kotlinx-html", "org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.5")

            version("compose", "1.2.0-alpha01-dev686")
            library("compose-gradle-plugin", "org.jetbrains.compose", "compose-gradle-plugin").versionRef("compose")

            library("serialization-json", "org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")

            library("dotenv", "co.uzzu.dotenv:gradle:1.2.0")

            version("apollo", "3.2.2")
            library("apollo-graphql", "com.apollographql.apollo3", "apollo-runtime").versionRef("apollo")
            library("apollo-normalized-cache", "com.apollographql.apollo3", "apollo-normalized-cache").versionRef("apollo")

        }
    }
}

include(
    ":common:app",
    ":common:server-contract",
    ":common:compose-ui",
    ":android",
    ":desktop",
    //  ":web", // disabled due to FATAL ERROR: Could not find "io.ktor:ktor-http-cio"
    ":server",
)

rootProject.name = "subscriptions-kt"