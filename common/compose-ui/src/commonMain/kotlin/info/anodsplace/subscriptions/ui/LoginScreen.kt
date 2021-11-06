package info.anodsplace.subscriptions.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import info.anodsplace.subscriptions.app.LoginViewModel

@Composable
fun LogInScreen(viewModel: LoginViewModel) {
    SideEffect {
        viewModel.login("alex")
    }
}
