package info.anodsplace.subscriptions.desktop

import androidx.compose.foundation.LocalScrollbarStyle
import androidx.compose.foundation.ScrollbarStyle
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import info.anodsplace.subscriptions.database.DefaultAppDatabase
import info.anodsplace.subscriptions.database.appDatabaseDriverFactory
import info.anodsplace.subscriptions.app.CommonRootViewModel
import info.anodsplace.subscriptions.app.AppCoroutineScope
import info.anodsplace.subscriptions.database.AppDatabase
import info.anodsplace.subscriptions.app.store.DefaultSubscriptionsStore
import info.anodsplace.subscriptions.app.store.SubscriptionsStore
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.swing.Swing
import org.koin.core.context.startKoin
import org.koin.dsl.bind
import org.koin.dsl.module

fun main() {
    val appCoroutineScope = AppCoroutineScope(Dispatchers.Swing.immediate)
    startKoin {
        koin.loadModules(listOf(module {
            single { appCoroutineScope } bind AppCoroutineScope::class
            single { HttpClient(CIO) {
                install(JsonFeature) { }
            } } bind HttpClient::class
            single { DefaultAppDatabase(appDatabaseDriverFactory(), get<AppCoroutineScope>()) } bind AppDatabase::class
            single { DefaultSubscriptionsStore(get(), get(), get()) } bind SubscriptionsStore::class
        }))
    }

    val root = CommonRootViewModel()
    application {
        val windowState = rememberWindowState()

        Window(
            onCloseRequest = ::exitApplication,
            state = windowState,
            title = "Todo"
        ) {
            Surface(modifier = Modifier.fillMaxSize()) {
                MaterialTheme {
                    CompositionLocalProvider(
                        LocalScrollbarStyle provides ScrollbarStyle(
                            minimalHeight = 16.dp,
                            thickness = 8.dp,
                            shape = MaterialTheme.shapes.small,
                            hoverDurationMillis = 300,
                            unhoverColor = MaterialTheme.colors.onSurface.copy(alpha = 0.12f),
                            hoverColor = MaterialTheme.colors.onSurface.copy(alpha = 0.50f)
                        )
                    ) {
                        TodoRootContent(root)
                    }
                }
            }
        }
    }
}