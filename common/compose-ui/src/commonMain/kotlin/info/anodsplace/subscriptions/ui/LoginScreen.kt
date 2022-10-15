package info.anodsplace.subscriptions.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import info.anodsplace.subscriptions.app.LoginViewEvent
import info.anodsplace.subscriptions.app.LoginViewModel
import info.anodsplace.subscriptions.app.LoginViewState

@Composable
fun LogInScreen(state: LoginViewState, onEvent: (LoginViewEvent) -> Unit) {
    SideEffect {
        onEvent(LoginViewEvent.Login)
    }
}
