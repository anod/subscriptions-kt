package info.anodsplace.subscriptions.android

import android.app.Application
import android.content.Context
import info.anodsplace.subscriptions.database.DefaultAppDatabase
import info.anodsplace.subscriptions.database.appDatabaseDriverFactory
import info.anodsplace.subscriptions.app.AppCoroutineScope
import info.anodsplace.subscriptions.database.AppDatabase
import info.anodsplace.subscriptions.app.store.DefaultSubscriptionsStore
import info.anodsplace.subscriptions.app.store.SubscriptionsStore
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import kotlinx.coroutines.Dispatchers
import org.koin.core.context.startKoin
import org.koin.dsl.bind
import org.koin.dsl.module

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        val appCoroutineScope = AppCoroutineScope(Dispatchers.Main.immediate)
        startKoin{
            koin.loadModules(listOf(module {
                single<Context> { this@App } bind Application::class
                single { appCoroutineScope } bind AppCoroutineScope::class
                single { HttpClient(CIO) } bind HttpClient::class
                single { DefaultAppDatabase(appDatabaseDriverFactory(context = get()), get<AppCoroutineScope>()) } bind AppDatabase::class
                single { DefaultSubscriptionsStore(get(), get()) } bind SubscriptionsStore::class
            }))
        }
    }
}
