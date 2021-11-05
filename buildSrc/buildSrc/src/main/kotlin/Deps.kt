object Deps {

    object JetBrains {
        object Kotlin {
            // __KOTLIN_COMPOSE_VERSION__
            internal const val VERSION = "1.5.31"
            const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$VERSION"
            const val testCommon = "org.jetbrains.kotlin:kotlin-test-common:$VERSION"
            const val testJunit = "org.jetbrains.kotlin:kotlin-test-junit:$VERSION"
            const val testJs = "org.jetbrains.kotlin:kotlin-test-js:$VERSION"
            const val testAnnotationsCommon = "org.jetbrains.kotlin:kotlin-test-annotations-common:$VERSION"
        }

        object Coroutines {
            private const val VERSION = "1.5.2"
            const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$VERSION"
        }

        object Compose {
            // __LATEST_COMPOSE_RELEASE_VERSION__
            private const val VERSION = "1.0.0-beta5"
            const val gradlePlugin = "org.jetbrains.compose:compose-gradle-plugin:$VERSION"
        }

        object Serialization {
            private const val VERSION = "1.3.0"
            const val gradlePlugin = "org.jetbrains.kotlin:kotlin-serialization:${Kotlin.VERSION}"
            const val json = "org.jetbrains.kotlinx:kotlinx-serialization-json:$VERSION"
        }
    }

    object Android {
        object Tools {
            object Build {
                const val gradlePlugin = "com.android.tools.build:gradle:4.2.0"
            }
        }
    }

    object AndroidX {
        object AppCompat {
            const val appCompat = "androidx.appcompat:appcompat:1.3.0"
        }

        object Activity {
            const val activityCompose = "androidx.activity:activity-compose:1.3.0"
        }
    }

    object SquareUp {
        object SQLDelight {
            private const val VERSION = "1.5.2"

            const val gradlePlugin = "com.squareup.sqldelight:gradle-plugin:$VERSION"
            const val androidDriver = "com.squareup.sqldelight:android-driver:$VERSION"
            const val sqliteDriver = "com.squareup.sqldelight:sqlite-driver:$VERSION"
            const val nativeDriver = "com.squareup.sqldelight:native-driver:$VERSION"
            const val sqljsDriver = "com.squareup.sqldelight:sqljs-driver:$VERSION"
            const val coroutines = "com.squareup.sqldelight:coroutines-extensions:$VERSION"
        }
    }

    object Ktor {
        private const val VERSION = "1.6.1"
        const val server = "io.ktor:ktor-server-core:$VERSION"
        const val client = "io.ktor:ktor-client-core:$VERSION"
        const val netty = "io.ktor:ktor-server-netty:$VERSION"
        const val html = "io.ktor:ktor-html-builder:$VERSION"
        const val auth = "io.ktor:ktor-auth:$VERSION"
        const val jwt = "io.ktor:ktor-auth-jwt:$VERSION"
        const val serialization = "io.ktor:ktor-serialization:$VERSION"
        const val clientSerialization = "io.ktor:ktor-client-serialization:$VERSION"
        const val js = "io.ktor:ktor-client-js:$VERSION"
        const val cio = "io.ktor:ktor-client-cio:$VERSION"
        const val ios = "io.ktor:ktor-client-ios:$VERSION"
    }

    object Koin {
        private const val VERSION = "3.1.3"
        const val core = "io.insert-koin:koin-core:$VERSION"
    }

    object Uzzu {
        const val dotenv = "co.uzzu.dotenv.gradle:1.2.0"
    }
}
