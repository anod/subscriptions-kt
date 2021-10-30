import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension
import org.jetbrains.compose.compose

plugins {
    kotlin("multiplatform")
    application
    id("org.jetbrains.compose")
}

kotlin {
    jvm {
        withJava()
    }
    js(IR) {
        browser {
            useCommonJs()
            binaries.executable()
        }
    }

    sourceSets {
        named("jvmMain") {
            dependencies {
                implementation(compose.runtime)
                implementation("io.ktor:ktor-server-core:1.6.1")
                implementation("io.ktor:ktor-server-netty:1.6.1")
                implementation("io.ktor:ktor-html-builder:1.6.1")
                implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.3")
            }
        }
        named("jsMain") {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.web.widgets)
                implementation(project(":common:utils"))
                implementation(project(":common:database"))
                implementation(project(":common:root"))
                implementation(project(":common:main"))
                implementation(project(":common:edit"))
                implementation(Deps.ArkIvanov.Decompose.decompose)
                implementation(Deps.ArkIvanov.MVIKotlin.mvikotlin)
                implementation(Deps.ArkIvanov.MVIKotlin.mvikotlinMain)
                implementation(npm("copy-webpack-plugin", "9.0.0"))
                implementation(npm("@material-ui/icons", "4.11.2"))
            }
        }
    }
}

application {
    mainClass.set("MainKt")
}

tasks.named<Copy>("jvmProcessResources") {
    val jsBrowserDistribution = tasks.named("jsBrowserDistribution")
    from(jsBrowserDistribution)
}

tasks.named<JavaExec>("run") {
    dependsOn(tasks.named<Jar>("jvmJar"))
    classpath(tasks.named<Jar>("jvmJar"))
}

// a temporary workaround for a bug in jsRun invocation - see https://youtrack.jetbrains.com/issue/KT-48273
afterEvaluate {
    rootProject.extensions.configure<NodeJsRootExtension> {
        versions.webpackDevServer.version = "4.0.0"
        versions.webpackCli.version = "4.9.0"
    }
}
