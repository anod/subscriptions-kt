plugins {
    `kotlin-dsl`
    id("co.uzzu.dotenv.gradle") version "2.0.0"
    id("com.google.devtools.ksp") version "1.8.20-1.0.11" apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
        mavenLocal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

buildscript {
    repositories {
        mavenLocal()
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    dependencies {
        classpath(libs.compose.gradle.plugin)
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.kotlin.serialization.gradle.plugin)
        classpath(libs.android.gradle.plugin)
    }
}