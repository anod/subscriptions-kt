package info.anodsplace.subscriptions.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import info.anodsplace.subscriptions.app.Route
import info.anodsplace.subscriptions.app.Router

@Composable
fun RootScreen(router: Router) {
    val route by router.route.collectAsState(Route.Initial)
    when (route) {
        is Route.LogIn -> LogInScreen((route as Route.LogIn).viewModel)
        is Route.Main -> MainScreen((route as Route.Main).viewModel)
        is Route.Edit -> EditScreen((route as Route.Edit).viewModel)
        Route.Initial -> { }
    }
}
