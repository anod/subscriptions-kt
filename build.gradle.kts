plugins {
    id("com.google.devtools.ksp") version "1.9.10-1.0.13" apply false
    kotlin("jvm").version("1.9.10") apply false
    kotlin("multiplatform").version("1.9.10") apply false
    kotlin("android").version("1.9.10") apply false
    kotlin("plugin.serialization").version("1.9.10") apply false
    alias(libs.plugins.dotenv.gradle)
    alias(libs.plugins.jetbrains.compose)
}
