plugins {
    `kotlin-dsl`
    id("co.uzzu.dotenv.gradle") version "1.2.0"
}

allprojects {
    repositories {
        google()
        mavenCentral()
        mavenLocal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}
