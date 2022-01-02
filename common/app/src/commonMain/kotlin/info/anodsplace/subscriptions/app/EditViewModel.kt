package info.anodsplace.subscriptions.app

import info.anodsplace.subscriptions.app.store.SubscriptionsStore
import kotlinx.coroutines.CoroutineScope

interface EditViewModel : ViewModel {

}

class CommonEditViewModel(
    override val store: SubscriptionsStore,
    override val currentScope: CoroutineScope,
) : EditViewModel {

}