package info.anodsplace.subscriptions.desktop

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import info.anodsplace.subscriptions.app.AppCoroutineScope
import info.anodsplace.subscriptions.app.CommonRouter
import info.anodsplace.subscriptions.app.graphql.GraphQLClient
import info.anodsplace.subscriptions.app.graphql.GraphQlApolloClient
import info.anodsplace.subscriptions.app.store.DefaultSubscriptionsStore
import info.anodsplace.subscriptions.app.store.SubscriptionsStore
import info.anodsplace.subscriptions.ui.ComposeAppTheme
import info.anodsplace.subscriptions.ui.RootScreen
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import kotlinx.coroutines.Dispatchers
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.logger.Logger
import org.koin.core.logger.PrintLogger
import org.koin.dsl.bind
import org.koin.dsl.module

fun main() {
    val logger = PrintLogger(Level.INFO)
    val appCoroutineScope = AppCoroutineScope(Dispatchers.Main.immediate)
    logger.info("Start app")
    startKoin {
        logger(logger)
        koin.loadModules(listOf(module {
            single { logger } bind Logger::class
            single { appCoroutineScope } bind AppCoroutineScope::class
            single { GraphQlApolloClient(get()) } bind GraphQLClient::class
            single { HttpClient(CIO) {
                install(JsonFeature) { }
            } } bind HttpClient::class
            single { DefaultSubscriptionsStore(get(), get(), get()) } bind SubscriptionsStore::class
        }))
    }

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Subscriptions",
            state = rememberWindowState(
                position = WindowPosition(alignment = Alignment.Center),
            ),
        ) {
            val root = CommonRouter(appCoroutineScope)

            ComposeAppTheme {
                Surface(color = MaterialTheme.colors.background) {
                    RootScreen(root)
                }
            }
        }
    }
}
