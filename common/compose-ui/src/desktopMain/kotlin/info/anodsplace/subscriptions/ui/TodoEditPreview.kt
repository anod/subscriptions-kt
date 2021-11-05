package info.anodsplace.subscriptions.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import info.anodsplace.subscriptions.ui.edit.EditViewModel
import info.anodsplace.subscriptions.ui.edit.TodoEditContent

@Composable
@Preview
fun TodoEditContentPreview() {
    TodoEditContent(TodoEditPreview())
}

class TodoEditPreview : EditViewModel {
    override val text: String
        get() = "Some text"

    override fun onTextChanged(value: String) {}
    override fun onDoneChanged(value: Boolean) {}
    override fun onCloseClicked() {}
}
