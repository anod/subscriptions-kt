package info.anodsplace.subscriptions.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import info.anodsplace.subscriptions.ui.edit.EditViewModel

@Composable
@Preview
fun TodoEditContentPreview() {
    EditScreen(TodoEditPreview())
}

class TodoEditPreview : EditViewModel {
    override val text: String
        get() = "Some text"

    override fun onTextChanged(value: String) {}
    override fun onDoneChanged(value: Boolean) {}
    override fun onCloseClicked() {}
}
