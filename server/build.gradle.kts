plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    application
    id("distribution")
}

kotlin {

    jvm {
        withJava()
    }

    sourceSets {
        named("jvmMain") {
            dependencies {
                implementation(project(":common:server-contract"))
                implementation(libs.ktor.server.core)
                implementation(libs.ktor.server.netty)
                implementation(libs.ktor.server.html)
                implementation(libs.ktor.server.auth)
                implementation(libs.ktor.server.jwt)
                implementation(libs.ktor.server.serialization)
                implementation(libs.kotlinx.html)
            }
        }
    }
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

val browserDist: Configuration by configurations.creating {
    isCanBeConsumed = false
    isCanBeResolved = true
}

dependencies {
    browserDist(
        project(
            mapOf(
                "path" to ":web",
                "configuration" to "browserDist"
            )
        )
    )
}

tasks.withType<Copy>().named("processResources") {
    from(browserDist)
}

fun isDevEnv(): Boolean = env.SBS_ENV.orNull() == "dev"

tasks.named<JavaExec>("run") {
    dependsOn(tasks.named<Jar>("jvmJar"))
    classpath(tasks.named<Jar>("jvmJar"))
    doFirst {
        environment("SBS_JWT_SECRET", env.SBS_JWT_SECRET.value)
        environment("SBS_JWT_AUDIENCE", env.SBS_JWT_AUDIENCE.value)
        environment("SBS_JWT_ISSUER", env.SBS_JWT_ISSUER.value)
        environment("SBS_ENV", env.SBS_ENV.value)
        environment("SBS_ENV_DEV", isDevEnv())
        environment("SBS_SERVER_PORT", env.SBS_SERVER_PORT.value.toInt())
    }
}