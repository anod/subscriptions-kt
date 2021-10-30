import io.ktor.application.call
import io.ktor.html.respondHtml
import io.ktor.http.HttpStatusCode
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.http.content.resources
import io.ktor.http.content.static
import kotlinx.html.*

fun HTML.index() {
    head {
        title("Hello from Ktor!")
        link("/static/styles.css", rel = "stylesheet", type = "text/css")
        link("/static/materialize.min.css", rel = "stylesheet", type = "text/css") {
            media = "screen,projection"
        }
    }
    body {
        div {
            +"Hello from Ktor"
        }
        div {
            id = "root"
        }
        script(src = "/static/web.js") {}
        script(src = "/static/materialize.min.js") {}
    }
}

fun main() {
    embeddedServer(Netty, port = 9090) {
        routing {
            get("/") {
                call.respondHtml(HttpStatusCode.OK, HTML::index)
            }
            static("/static") {
                resources()
            }
        }
    }.start(wait = true)
}