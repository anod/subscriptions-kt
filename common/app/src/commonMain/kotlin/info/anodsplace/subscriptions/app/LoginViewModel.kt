package info.anodsplace.subscriptions.app

import info.anodsplace.subscriptions.app.store.*

data class LoginViewState(
    val username: String = "alex"
) : StoreState

sealed interface LoginViewEvent : StoreEvent {
    object Login: LoginViewEvent
}

sealed interface LoginViewAction : StoreAction

interface LoginViewModel : ViewModel<LoginViewState, LoginViewEvent, LoginViewAction>

class CommonLoginViewModel(
    private val store: SubscriptionsStore,
    override val viewModelScope: ViewModelScope,
) : LoginViewModel, BaseStore<LoginViewState, LoginViewEvent, LoginViewAction>(storeScope = viewModelScope) {

   init {
       state = LoginViewState()
   }

    override fun handleEvent(event: LoginViewEvent) {
        when (event) {
            LoginViewEvent.Login -> store.handleEvent(SubscriptionEvent.Login(state.username, state.username))
        }
    }

}