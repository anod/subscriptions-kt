package info.anodsplace.subscriptions.app

import info.anodsplace.subscriptions.app.store.SubscriptionAction
import info.anodsplace.subscriptions.app.store.SubscriptionsStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

sealed interface Route {
    data class Initial(val initial: Boolean = true) : Route
    data class LogIn(val login: Boolean = true) : Route
    data class Main(val main: Boolean = true) : Route
    data class Edit(val id: Long) : Route
}

interface Router {
    val route: StateFlow<Route>
    fun createViewModel(route: Route): ViewModel<*, *, *>
}

class CommonRouter(appScope: AppCoroutineScope) : Router, KoinComponent {
    private val store: SubscriptionsStore by inject()
    override val route: MutableStateFlow<Route> = MutableStateFlow(
        if (store.state.isLoggedIn) Route.Main() else Route.LogIn()
    )

    init {
        appScope.launch {
            store.actions
                .filterIsInstance<SubscriptionAction.Navigate>()
                .collect {
                    route.value = it.route
                }
        }
    }

    override fun createViewModel(route: Route): ViewModel<*, *, *> {
        return when (route) {
            is Route.LogIn -> get<LoginViewModel>()
            is Route.Main -> get<MainViewModel>()
            is Route.Edit -> get<EditViewModel> { parametersOf(route.id) }
            else -> throw IllegalStateException("Not supported route $route")
        }
    }
}