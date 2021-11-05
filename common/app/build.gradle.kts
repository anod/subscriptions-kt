plugins {
    id("multiplatform-setup")
    id("android-setup")
    id("com.squareup.sqldelight")
}

sqldelight {
    database("SubscriptionsDatabase") {
        packageName = "info.anodsplace.subscriptions.database"
    }
}

kotlin {
    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":common:server-contract"))
                implementation(Deps.JetBrains.Coroutines.core)
                implementation(Deps.Koin.core)
                implementation(Deps.Ktor.client)
                implementation(Deps.SquareUp.SQLDelight.coroutines)
            }
        }

        androidMain {
            dependencies {
                implementation(Deps.SquareUp.SQLDelight.androidDriver)
                implementation(Deps.SquareUp.SQLDelight.sqliteDriver)
            }
        }

        desktopMain {
            dependencies {
                implementation(Deps.SquareUp.SQLDelight.sqliteDriver)
            }
        }

        jsMain {
            dependencies {
                implementation(Deps.SquareUp.SQLDelight.sqljsDriver)
            }
        }
    }
}
