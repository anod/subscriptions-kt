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
                implementation(Deps.Ktor.Server.core)
                implementation(Deps.Ktor.Server.netty)
                implementation(Deps.Ktor.Server.html)
                implementation(Deps.Ktor.Server.auth)
                implementation(Deps.Ktor.Server.jwt)
                implementation(Deps.Ktor.Server.serialization)
                implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.3")
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