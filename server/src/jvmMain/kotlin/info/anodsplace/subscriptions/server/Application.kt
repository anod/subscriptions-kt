package info.anodsplace.subscriptions.server

import info.anodsplace.subscriptions.server.contract.LoginRequest
import info.anodsplace.subscriptions.server.contract.LoginResponse
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.html.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.html.*

fun HTML.index() {
    head {
        title("Hello from Ktor!")
        link("/static/styles.css", rel = "stylesheet", type = "text/css")
        link("/static/materialize.min.css", rel = "stylesheet", type = "text/css") {
            media = "screen,projection"
        }
        meta(name = "viewport", content = "width=device-width, initial-scale=1.0")
        link("https://fonts.googleapis.com/icon?family=Material+Icons", rel = "stylesheet", type = "text/css")
    }
    body {
        div { id = "root" }
        script(src = "web.js") { }
        script(src = "/static/materialize.min.js") { }
    }
}

fun Application.module(testing: Boolean = false) {
    val jwtToken = JwtToken(environment)
    install(ContentNegotiation) {
        json()
    }
    install(CORS) {
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Delete)
        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.Authorization)
        allowHeader(HttpHeaders.AccessControlAllowOrigin)
        allowSameOrigin = true
        anyHost()
    }
    install(Compression) {
        gzip()
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
        get("/ping") {
            call.respondText("OK", status = HttpStatusCode.OK,)
        }
        post("/login") {
            val credentials = call.receive<LoginRequest>()
            val user = PasswordAuthenticator(credentials).authenticate()
            val token = jwtToken.forUser(user)
            call.respond(LoginResponse(token = token, userId = user.id))
        }
        staticResources("/static", "resources")
    }
}