package info.anodsplace.subscriptions.android

import android.app.Application
import android.content.Context
import info.anodsplace.subscriptions.AndroidCurrency
import info.anodsplace.subscriptions.app.AppCoroutineScope
import info.anodsplace.subscriptions.app.createCommonAppModule
import info.anodsplace.subscriptions.app.currency.Currency
import info.anodsplace.subscriptions.app.graphql.GraphQLClient
import info.anodsplace.subscriptions.app.graphql.GraphQlApolloClient
import info.anodsplace.subscriptions.app.store.DefaultSubscriptionsStore
import info.anodsplace.subscriptions.app.store.SubscriptionsStore
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.Dispatchers
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        val appCoroutineScope = AppCoroutineScope(Dispatchers.Main.immediate)
        startKoin {
            koin.loadModules(
                listOf(
                    module {
                        single<Context> { this@App }
                        single<Application> { this@App }
                        single { appCoroutineScope } bind AppCoroutineScope::class
                        single {
                            HttpClient(CIO) {
                                install(ContentNegotiation) {
                                    json()
                                }
                            }
                        } bind HttpClient::class
                        single { GraphQlApolloClient(get()) } bind GraphQLClient::class
                        single { AndroidCurrency() } bind Currency::class
                        singleOf(::DefaultSubscriptionsStore) bind SubscriptionsStore::class
                    },
                    createCommonAppModule()
                )
            )
        }
    }
}
