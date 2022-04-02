package info.anodsplace.subscriptions.web

import info.anodsplace.subscriptions.app.AppCoroutineScope
import info.anodsplace.subscriptions.app.CommonRouter
import info.anodsplace.subscriptions.app.graphql.GraphQLClient
import info.anodsplace.subscriptions.app.graphql.GraphQlApolloClient
import info.anodsplace.subscriptions.app.store.DefaultSubscriptionsStore
import info.anodsplace.subscriptions.app.store.SubscriptionsStore
import kotlinx.browser.document
import org.jetbrains.compose.web.css.Style
import org.jetbrains.compose.web.renderComposable
import org.w3c.dom.HTMLElement
import io.ktor.client.*
import io.ktor.client.engine.js.*
import io.ktor.client.features.json.*
import io.ktor.client.features.logging.*
import kotlinx.coroutines.Dispatchers
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.logger.Logger
import org.koin.core.logger.PrintLogger
import org.koin.dsl.bind
import org.koin.dsl.module

class KoinKtorLoggerBridge(private val koinLogger: Logger) : io.ktor.client.features.logging.Logger {
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
                HttpClient(Js.create()) {
                    install(JsonFeature) { }
                    install(Logging) {
                        this.logger = KoinKtorLoggerBridge(koinLogger)
                    }
                } } bind HttpClient::class
            single { GraphQlApolloClient(get()) } bind GraphQLClient::class
            single { DefaultSubscriptionsStore(get(), get(), get()) } bind SubscriptionsStore::class
        }))
    }

    val router = CommonRouter(appCoroutineScope)
    renderComposable(root = rootElement) {
        Style(AppStylesheet)
        TodoRootUi(router)
    }
}
