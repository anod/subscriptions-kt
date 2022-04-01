
dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            library("androidx-appcompat","androidx.appcompat:appcompat:1.4.1")
            library("androidx-activity-compose","androidx.activity:activity-compose:1.3.0")
            library("androidx-core", "androidx.core:core-ktx:1.7.0")
            library("android-gradle-plugin","com.android.tools.build:gradle:7.0.4")

            version("ktor", "1.6.7")
            library("ktor-client-core","io.ktor", "ktor-client-core").versionRef("ktor")
            library("ktor-client-logging","io.ktor", "ktor-client-logging").versionRef("ktor")
            library("ktor-client-serialization","io.ktor", "ktor-client-serialization").versionRef("ktor")
            library("ktor-client-js","io.ktor", "ktor-client-js").versionRef("ktor")
            library("ktor-client-cio","io.ktor", "ktor-client-cio").versionRef("ktor")

            library("ktor-server-core","io.ktor", "ktor-server-core").versionRef("ktor")
            library("ktor-server-netty","io.ktor", "ktor-server-netty").versionRef("ktor")
            library("ktor-server-html","io.ktor", "ktor-html-builder").versionRef("ktor")
            library("ktor-server-auth","io.ktor", "ktor-auth").versionRef("ktor")
            library("ktor-server-jwt","io.ktor", "ktor-auth-jwt").versionRef("ktor")
            library("ktor-server-serialization","io.ktor", "ktor-serialization").versionRef("ktor")
            library("koin-core","io.insert-koin:koin-core:3.1.4")

            version("kotlin", "1.6.10")
            library("kotlin-gradle-plugin","org.jetbrains.kotlin", "kotlin-gradle-plugin").versionRef("kotlin")
            library("kotlin-test-common","org.jetbrains.kotlin", "kotlin-test-common").versionRef("kotlin")
            library("kotlin-test-junit","org.jetbrains.kotlin", "kotlin-test-junit").versionRef("kotlin")
            library("kotlin-test-js","org.jetbrains.kotlin", "kotlin-test-js").versionRef("kotlin")
            library("kotlin-test-annotations","org.jetbrains.kotlin", "kotlin-test-annotations-common").versionRef("kotlin")
            library("kotlin-serialization-gradle-plugin","org.jetbrains.kotlin", "kotlin-serialization").versionRef("kotlin")

            version("coroutines", "1.6.0")
            library("coroutines-core","org.jetbrains.kotlinx", "kotlinx-coroutines-core").versionRef("coroutines")
            library("kotlinx-html", "org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.3")

            version("compose", "1.0.1")
            library("compose-gradle-plugin","org.jetbrains.compose", "compose-gradle-plugin").versionRef("compose")

            library("serialization-json","org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")

            version("sqldelight", "1.5.3")
            library("sqldelight-gradle-plugin","com.squareup.sqldelight", "gradle-plugin").versionRef("sqldelight")
            library("sqldelight-android","com.squareup.sqldelight", "android-driver").versionRef("sqldelight")
            library("sqldelight-sqlite","com.squareup.sqldelight", "sqlite-driver").versionRef("sqldelight")
            library("sqldelight-native","com.squareup.sqldelight", "native-driver").versionRef("sqldelight")
            library("sqldelight-sqljs","com.squareup.sqldelight", "sqljs-driver").versionRef("sqldelight")
            library("sqldelight-coroutines","com.squareup.sqldelight", "coroutines-extensions").versionRef("sqldelight")

            library("dotenv","co.uzzu.dotenv:gradle:1.2.0")

            library("apollo-graphql", "com.apollographql.apollo3:apollo-runtime:3.2.0")
        }
    }
}

include(
    ":common:app",
    ":common:server-contract",
    ":common:compose-ui",
    ":android",
    ":desktop",
    ":web",
    ":server",
)

rootProject.name = "subscriptions-kt"