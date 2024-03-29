import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

kotlin {
    jvm {
        withJava()
    }
    sourceSets {
        named("jvmMain") {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(project(":common:app"))
                implementation(project(":common:compose-ui"))
                implementation(compose.material)
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.cio)
                implementation(libs.ktor.client.serialization)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.koin.core)
                implementation(libs.coroutines.swing)
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "info.anodsplace.subscriptions.desktop.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "Subscriptions"
            packageVersion = "1.0.0"

            windows {
                menuGroup = "Subscriptions"
                // see https://wixtoolset.org/documentation/manual/v3/howtos/general/generate_guids.html
                upgradeUuid = "87cd8d37-ae83-45d1-9433-b1cb83c26154"
            }
        }
    }
}