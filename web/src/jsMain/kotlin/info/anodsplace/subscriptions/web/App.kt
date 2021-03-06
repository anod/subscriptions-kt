package info.anodsplace.subscriptions.web

import info.anodsplace.subscriptions.JsCurrency
import info.anodsplace.subscriptions.app.AppCoroutineScope
import info.anodsplace.subscriptions.app.CommonRouter
import info.anodsplace.subscriptions.app.Currency
import info.anodsplace.subscriptions.app.graphql.GraphQLClient
import info.anodsplace.subscriptions.app.graphql.GraphQlApolloClient
import info.anodsplace.subscriptions.app.store.DefaultSubscriptionsStore
import info.anodsplace.subscriptions.app.store.SubscriptionsStore
import io.ktor.client.*
import io.ktor.client.engine.js.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.browser.document
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import org.jetbrains.compose.web.css.Style
import org.jetbrains.compose.web.renderComposable
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.logger.Logger
import org.koin.core.logger.PrintLogger
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.w3c.dom.HTMLElement

class KoinKtorLoggerBridge(private val koinLogger: Logger) : io.ktor.client.plugins.logging.Logger {
    override fun log(message: String) {
        koinLogger.log(Level.INFO, message)
    }
}

fun main() {
    val logger = PrintLogger(Level.INFO)
    val rootElement = document.getElementById("root") as HTMLElement
    val appCoroutineScope = AppCoroutineScope(Dispatchers.Main.immediate)
    logger.info("Start app")
    startKoin {
        logger(logger)
        koin.loadModules(listOf(module {
            single { logger } bind Logger::class
            single { appCoroutineScope } bind AppCoroutineScope::class
            single {
                val koinLogger = get<Logger>()
                HttpClient(Js) {
                    install(ContentNegotiation) { json(Json) }
                    install(Logging) {
                        this.logger = KoinKtorLoggerBridge(koinLogger)
                    }
                }
            } bind HttpClient::class
            single { GraphQlApolloClient(get()) } bind GraphQLClient::class
            singleOf(::DefaultSubscriptionsStore) bind SubscriptionsStore::class
            singleOf(::JsCurrency) bind Currency::class
        }))
    }

    val router = CommonRouter(appCoroutineScope)
    renderComposable(root = rootElement) {
        Style(AppStylesheet)
        TodoRootUi(router)
    }
}
