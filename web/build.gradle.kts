import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension
import org.jetbrains.compose.compose

plugins {
    kotlin("multiplatform")
    application
    id("org.jetbrains.compose")
    kotlin("plugin.serialization")
    id("co.uzzu.dotenv.gradle") version "1.2.0"
}
kotlin {

    jvm("server") {
        withJava()
    }

    js(IR) {
        browser {
            useCommonJs()
            binaries.executable()
        }
    }

    sourceSets {
        named("serverMain") {
            dependencies {
                implementation(compose.runtime)
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
        named("jsMain") {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.web.widgets)
                implementation(project(":common:app"))
                implementation(Deps.Ktor.Client.core)
                implementation(Deps.Ktor.Client.js)
                implementation(Deps.Ktor.Client.serialization)
                implementation(Deps.Koin.core)
                implementation(npm("copy-webpack-plugin", "9.0.0"))
                implementation(npm("@material-ui/icons", "4.11.2"))
            }
        }
    }
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

tasks.named<Copy>("serverProcessResources") {
    val jsBrowserDistribution = tasks.named("jsBrowserDistribution")
    from(jsBrowserDistribution)
}

tasks.named<JavaExec>("run") {
    dependsOn(tasks.named<Jar>("serverJar"))
    classpath(tasks.named<Jar>("serverJar"))
    doFirst {
        environment("SBS_JWT_SECRET", env.SBS_JWT_SECRET.value)
        environment("SBS_JWT_AUDIENCE", env.SBS_JWT_SECRET.value)
        environment("SBS_ENV", env.SBS_ENV.value)
        environment("SBS_ENV_DEV", env.SBS_ENV.orNull() == "dev")
        environment("SBS_WEB_PORT", env.SBS_WEB_PORT.value.toInt())
    }
}

// a temporary workaround for a bug in jsRun invocation - see https://youtrack.jetbrains.com/issue/KT-48273
afterEvaluate {
    rootProject.extensions.configure<NodeJsRootExtension> {
        versions.webpackDevServer.version = "4.0.0"
        versions.webpackCli.version = "4.9.0"
    }
}
