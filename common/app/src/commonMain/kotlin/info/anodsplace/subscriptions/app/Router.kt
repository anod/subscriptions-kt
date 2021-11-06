package info.anodsplace.subscriptions.app

import info.anodsplace.subscriptions.app.store.SubscriptionAction
import info.anodsplace.subscriptions.app.store.SubscriptionSideEffect
import info.anodsplace.subscriptions.app.store.SubscriptionsStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

sealed interface Route {
    object Initial : Route
    class LogIn(val viewModel: LoginViewModel) : Route
    class Main(val viewModel: MainViewModel) : Route
    class Edit(val viewModel: EditViewModel) : Route
}

interface Router : KoinComponent {
    val route: StateFlow<Route>
}

class CommonRouter(appScope: AppCoroutineScope): Router {
    private val store: SubscriptionsStore by inject()
    override val route: MutableStateFlow<Route> = MutableStateFlow(
        if (store.isLoggedIn) createMain() else createLogin()
    )

    init {
        appScope.launch {
            store.sideEffect.collect {
                if (it is SubscriptionSideEffect.Action) {
                    if (it.action is SubscriptionAction.LoggedIn) {
                        route.value = createMain()
                    }
                }
            }
        }
    }

    private fun createScope(): CoroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private fun createLogin(): Route = Route.LogIn(CommonLoginViewModel(store, createScope()))
    private fun createMain(): Route = Route.Main(CommonMainViewModel(store, createScope()))
}