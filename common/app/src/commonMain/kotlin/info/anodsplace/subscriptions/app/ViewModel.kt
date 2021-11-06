package info.anodsplace.subscriptions.app

import info.anodsplace.subscriptions.app.store.SubscriptionsStore
import kotlinx.coroutines.CoroutineScope
import org.koin.core.component.KoinComponent

interface ViewModel : KoinComponent {
    val store: SubscriptionsStore
    val currentScope: CoroutineScope
}