package info.anodsplace.subscriptions.web

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import info.anodsplace.subscriptions.app.Route
import info.anodsplace.subscriptions.app.Router
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.keywords.auto
import org.jetbrains.compose.web.css.bottom
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.left
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.position
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.right
import org.jetbrains.compose.web.css.top
import org.jetbrains.compose.web.css.width

@Composable
fun TodoRootUi(router: Router) {
    Card(
        attrs = {
            style {
                position(Position.Absolute)
                height(700.px)
                property("max-width", 640.px)
                top(0.px)
                bottom(0.px)
                left(0.px)
                right(0.px)
                property("margin", auto)
            }
        }
    ) {
        val current by router.route.collectAsState(Route.Initial)
        Crossfade(
            target = current,
            attrs = {
                style {
                    width(100.percent)
                    height(100.percent)
                    position(Position.Relative)
                    left(0.px)
                    top(0.px)
                }
            }
        ) { route ->
            when (route) {
                Route.Initial -> { }
                is Route.Main -> MainUi(route.viewModel)
                is Route.Edit -> EditUi(route.viewModel)
                is Route.LogIn -> LoginUi(route.viewModel)
            }
        }
    }
}
