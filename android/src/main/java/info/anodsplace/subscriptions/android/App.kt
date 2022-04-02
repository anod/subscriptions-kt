package info.anodsplace.subscriptions.android

import android.app.Application
import android.content.Context
import info.anodsplace.subscriptions.app.AppCoroutineScope
import info.anodsplace.subscriptions.app.graphql.GraphQLClient
import info.anodsplace.subscriptions.app.graphql.GraphQlApolloClient
import info.anodsplace.subscriptions.app.store.DefaultSubscriptionsStore
import info.anodsplace.subscriptions.app.store.SubscriptionsStore
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
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
                single { HttpClient(CIO) {
                    install(JsonFeature) { }
                } } bind HttpClient::class
                single { GraphQlApolloClient(get()) } bind GraphQLClient::class
                single { DefaultSubscriptionsStore(get(), get(), get(),) } bind SubscriptionsStore::class
            }))
        }
    }
}
