import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform") // kotlin("jvm") doesn't work well in IDEA/AndroidStudio (https://github.com/JetBrains/compose-jb/issues/22)
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
                implementation(Deps.Ktor.client)
                implementation(Deps.Ktor.cio)
                implementation(Deps.Ktor.clientSerialization)
                implementation(Deps.Koin.core)
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "info.anodsplace.subscriptions.desktop.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "SubscriptionsDesktop"
            packageVersion = "1.0.0"

            modules("java.sql")

            windows {
                menuGroup = "Subscriptions"
                // see https://wixtoolset.org/documentation/manual/v3/howtos/general/generate_guids.html
                upgradeUuid = "BF9CDA6A-1391-46D5-9ED5-383D6E68CCEB"
            }
        }
    }
}
