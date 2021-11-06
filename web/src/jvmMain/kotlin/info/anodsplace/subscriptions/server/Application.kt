package info.anodsplace.subscriptions.server

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.html.respondHtml
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import kotlinx.html.*
import info.anodsplace.subscriptions.server.contract.LoginRequest
import info.anodsplace.subscriptions.server.contract.LoginResponse

fun HTML.index() {
    head {
        title("Hello from Ktor!")
        link("/static/styles.css", rel = "stylesheet", type = "text/css")
        link("/static/materialize.min.css", rel = "stylesheet", type = "text/css") {
            media = "screen,projection"
        }
        meta(name = "viewport", content = "width=device-width, initial-scale=1.0")
    }
    body {
        div { id = "root" }
        script(src = "/static/web.js") {}
        script(src = "/static/materialize.min.js") {}
    }
}

fun Application.module(testing: Boolean = false) {
    val jwtToken = JwtToken(environment)
    install(ContentNegotiation) {
        json()
    }
    install(Authentication) {
        jwt("auth-jwt") {
            realm = jwtToken.realm
            verifier(jwtToken.verifier)
            validate { credential ->
                if (credential.payload.getClaim("username").asString() != "") {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
        }
    }
    routing {
        get("/") {
            call.respondHtml(HttpStatusCode.OK, HTML::index)
        }
        post("/login") {
            val credentials = call.receive<LoginRequest>()
            val user = PasswordAuthenticator(credentials).authenticate()
            val token = jwtToken.forUser(user)
            call.respond(LoginResponse(token = token))
        }
        static("/static") {
            resources()
        }
    }
}