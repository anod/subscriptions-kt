package info.anodsplace.subscriptions.web

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import info.anodsplace.subscriptions.app.LoginViewModel

@Composable
fun LoginUi(viewModel: LoginViewModel) {

    SideEffect {
        viewModel.login("alex")
    }
}