package info.anodsplace.subscriptions.web

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import info.anodsplace.subscriptions.app.Route
import info.anodsplace.subscriptions.app.RouteState
import info.anodsplace.subscriptions.app.Router

@Composable
fun TodoRootUi(router: Router) {
    val route by router.route.collectAsState(RouteState.Initial)
    when (route) {
        RouteState.Initial -> { }
        is RouteState.Main -> MainUi((route as RouteState.Main).viewModel)
        is RouteState.Edit -> EditUi((route as RouteState.Edit).viewModel)
        is RouteState.LogIn -> LoginUi((route as RouteState.LogIn).viewModel)
    }
}