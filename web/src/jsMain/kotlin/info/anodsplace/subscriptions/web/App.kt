package info.anodsplace.subscriptions.web

import info.anodsplace.subscriptions.app.AppCoroutineScope
import info.anodsplace.subscriptions.database.AppDatabase
import info.anodsplace.subscriptions.app.CommonRootViewModel
import info.anodsplace.subscriptions.app.store.DefaultSubscriptionsStore
import info.anodsplace.subscriptions.app.store.SubscriptionsStore
import info.anodsplace.subscriptions.database.DefaultAppDatabase
import info.anodsplace.subscriptions.database.appDatabaseDriverFactory
import kotlinx.browser.document
import org.jetbrains.compose.web.css.Style
import org.jetbrains.compose.web.renderComposable
import org.jetbrains.compose.web.ui.Styles
import org.w3c.dom.HTMLElement
import io.ktor.client.*
import io.ktor.client.engine.js.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.stateIn
import org.koin.core.context.startKoin
import org.koin.dsl.bind
import org.koin.dsl.module

fun main() {
    val rootElement = document.getElementById("root") as HTMLElement

    val appCoroutineScope = AppCoroutineScope(Dispatchers.Main.immediate)
    startKoin {
        koin.loadModules(listOf(module {
            single { appCoroutineScope } bind AppCoroutineScope::class
            single { HttpClient(Js.create()) } bind HttpClient::class
            single { DefaultAppDatabase(appDatabaseDriverFactory()
                .stateIn(get<AppCoroutineScope>(), SharingStarted.Eagerly, initialValue = null)
                .filterNotNull(),
                get<AppCoroutineScope>())
            } bind AppDatabase::class
            single { DefaultSubscriptionsStore(get(), get()) } bind SubscriptionsStore::class
        }))
    }

    val root = CommonRootViewModel()
    renderComposable(root = rootElement) {
        Style(Styles)

        TodoRootUi(root)
    }
}
