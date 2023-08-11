plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

kotlin {
    jvm("desktop")
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
            }
        }

        named("androidMain") {
            dependencies {
                implementation(libs.androidx.appcompat)
                implementation(libs.androidx.core)
            }
        }
        named("commonMain") {
            dependencies {
                implementation(project(":common:app"))
                implementation(libs.coroutines.core)
                implementation(libs.koin.core)
            }
        }
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }
}
