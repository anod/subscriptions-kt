package info.anodsplace.subscriptions.ui

import androidx.compose.runtime.*
import info.anodsplace.subscriptions.app.*

@Composable
fun RootScreen(router: Router) {
    val route by router.route.collectAsState(Route.Initial())
    when (route) {
        is Route.Initial -> {}
        is Route.Main -> {
            val viewModel by remember { mutableStateOf(router.createViewModel(route) as MainViewModel) }
            val state by viewModel.states.collectAsState(viewModel.state)
            MainScreen(
                state = state,
                onEvent = { viewModel.handleEvent(it)},
                formatPrice = { price, currencyCode -> viewModel.formatPrice(price, currencyCode)
            })
        }
        is Route.Edit -> {
            val viewModel by remember { mutableStateOf(router.createViewModel(route) as EditViewModel) }
            val state by viewModel.states.collectAsState(viewModel.state)
            EditScreen(
                state = state,
                onEvent = { viewModel.handleEvent(it) }
            )
        }
        is Route.LogIn -> {
            val viewModel by remember { mutableStateOf(router.createViewModel(route) as LoginViewModel) }
            val state by viewModel.states.collectAsState(viewModel.state)
            LogInScreen(
                state = state,
                onEvent = { viewModel.handleEvent(it) }
            )
        }
    }
}
