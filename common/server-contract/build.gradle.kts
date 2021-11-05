plugins {
    id("kotlin-multiplatform")
    kotlin("plugin.serialization")
}

kotlin {
    jvm("server")
    js(IR) {
        browser()
    }
    sourceSets {
        named("commonMain") {
            dependencies {
                implementation(Deps.JetBrains.Serialization.json)
                implementation(Deps.Ktor.client)
                implementation(Deps.Ktor.clientSerialization)
            }
        }
    }
}
