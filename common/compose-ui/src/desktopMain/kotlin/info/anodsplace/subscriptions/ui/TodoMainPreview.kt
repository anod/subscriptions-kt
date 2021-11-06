package info.anodsplace.subscriptions.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import info.anodsplace.subscriptions.database.Subscription
import info.anodsplace.subscriptions.ui.main.MainViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Preview
@Composable
fun TodoMainContentPreview() {
    MainScreen(TodoMainPreview())
}

class TodoMainPreview : MainViewModel {
    override val subscriptions: StateFlow<List<Subscription>> = MutableStateFlow(listOf(
        Subscription(0,"", "", "", 0, 0)
    ))
    override val text: String = ""
    override fun onItemClicked(id: Long) {}
    override fun onItemDoneChanged(id: Long, isDone: Boolean) {}
    override fun onItemDeleteClicked(id: Long) {}
    override fun onInputTextChanged(text: String) {}
    override fun onAddItemClicked() {}
}
