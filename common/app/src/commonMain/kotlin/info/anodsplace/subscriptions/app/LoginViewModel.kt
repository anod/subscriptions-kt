package info.anodsplace.subscriptions.app

import info.anodsplace.subscriptions.app.store.SubscriptionAction
import info.anodsplace.subscriptions.app.store.SubscriptionsStore
import kotlinx.coroutines.CoroutineScope

interface LoginViewModel : ViewModel {
    fun login(username: String)
}

class CommonLoginViewModel(
    override val store: SubscriptionsStore,
    override val currentScope: CoroutineScope,
) : LoginViewModel {

    override fun login(username: String) {
        store.dispatch(SubscriptionAction.Login(username, username))
    }

}