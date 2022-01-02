package info.anodsplace.subscriptions.app

import info.anodsplace.subscriptions.app.store.SubscriptionSideEffect
import info.anodsplace.subscriptions.app.store.SubscriptionsStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

sealed interface Route {
    object Initial: Route
    object LogIn: Route
    object Main: Route
    object Edit: Route
}

sealed class RouteState(val type: Route) {
    object Initial : RouteState(Route.Initial)
    class LogIn(val viewModel: LoginViewModel) : RouteState(Route.LogIn)
    class Main(val viewModel: MainViewModel) : RouteState(Route.Main)
    class Edit(val viewModel: EditViewModel) : RouteState(Route.Edit)
}

interface Router : KoinComponent {
    val route: StateFlow<RouteState>
}

class CommonRouter(appScope: AppCoroutineScope): Router {
    private val store: SubscriptionsStore by inject()
    override val route: MutableStateFlow<RouteState> = MutableStateFlow(
        if (store.isLoggedIn) createMain() else createLogin()
    )

    init {
        appScope.launch {
            store.sideEffect.collect {
                if (it is SubscriptionSideEffect.Navigate) {
                    route.value = createRoute(it.route)
                }
            }
        }
    }

    private fun createRoute(routeType: Route): RouteState {
        return when (routeType) {
            Route.Initial -> RouteState.Initial
            Route.LogIn -> createLogin()
            Route.Main -> createMain()
            Route.Edit -> createEdit()
        }
    }

    private fun createScope(): CoroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private fun createLogin(): RouteState = RouteState.LogIn(CommonLoginViewModel(store, createScope()))
    private fun createMain(): RouteState = RouteState.Main(CommonMainViewModel(store, createScope()))
    private fun createEdit(): RouteState = RouteState.Edit(CommonEditViewModel(store, createScope()))
}