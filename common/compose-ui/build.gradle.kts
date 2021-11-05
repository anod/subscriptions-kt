plugins {
    id("multiplatform-compose-setup")
    id("android-setup")
}

kotlin {
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(project(":common:app"))
                implementation(Deps.JetBrains.Coroutines.core)
                implementation(Deps.Koin.core)
            }
        }
    }
}
