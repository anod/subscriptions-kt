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
            MainScreen(viewModel)
        }
        is Route.Edit -> {
            val viewModel by remember { mutableStateOf(router.createViewModel(route) as EditViewModel) }
            EditScreen(viewModel)
        }
        is Route.LogIn -> {
            val viewModel by remember { mutableStateOf(router.createViewModel(route) as LoginViewModel) }
            LogInScreen(viewModel)
        }
    }
}
