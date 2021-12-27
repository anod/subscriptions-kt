package info.anodsplace.subscriptions.web

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import info.anodsplace.subscriptions.app.Route
import info.anodsplace.subscriptions.app.Router

@Composable
fun TodoRootUi(router: Router) {
    val route by router.route.collectAsState(Route.Initial)
    when (route) {
        Route.Initial -> { }
        is Route.Main -> MainUi((route as Route.Main).viewModel)
        is Route.Edit -> EditUi((route as Route.Edit).viewModel)
        is Route.LogIn -> LoginUi((route as Route.LogIn).viewModel)
    }
}
 /**
 style {
 width(100.percent)
 height(100.percent)
 display(DisplayStyle.Flex)
 flexFlow(FlexDirection.Column, FlexWrap.Nowrap)
 }
  )*/