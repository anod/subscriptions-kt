package info.anodsplace.subscriptions.ui.root

import androidx.compose.runtime.Composable
import info.anodsplace.subscriptions.app.Child
import info.anodsplace.subscriptions.app.RootViewModel
import info.anodsplace.subscriptions.ui.edit.TodoEditContent
import info.anodsplace.subscriptions.ui.main.TodoMainContent

@Composable
fun TodoRootContent(viewModel: RootViewModel) {
    when (val child = viewModel.instance) {
        is Child.Main -> TodoMainContent(child.viewModel)
        is Child.Edit -> TodoEditContent(child.viewModel)
    }
}
