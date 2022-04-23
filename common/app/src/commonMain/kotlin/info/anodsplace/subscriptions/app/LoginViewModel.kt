package info.anodsplace.subscriptions.app

import info.anodsplace.subscriptions.app.store.SubscriptionAction
import info.anodsplace.subscriptions.app.store.SubscriptionsStore

interface LoginViewModel : ViewModel {
    fun login(username: String)
}

class CommonLoginViewModel(
    private val store: SubscriptionsStore,
    override val viewModelScope: ViewModelScope,
) : LoginViewModel {

    override fun login(username: String) {
        store.dispatch(SubscriptionAction.Login(username, username))
    }

}