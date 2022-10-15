package info.anodsplace.subscriptions.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import info.anodsplace.subscriptions.app.*
import info.anodsplace.subscriptions.app.store.Subscription

@Composable
fun EditScreen(state: EditViewState, onEvent: (EditViewEvent) -> Unit) {
    if (state.screenState is ScreenState.Ready) {
        EditSubscription(state = state, onEvent = onEvent)
    } else {
        CircularProgressIndicator()
    }
}

@Composable
fun EditSubscription(state: EditViewState, onEvent: (EditViewEvent) -> Unit) {
    val titleText = if (state.isEditMode) "Edit ${state.subscription.name}" else "New subscription"
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TopAppBar(
            title = { Text(text = titleText) },
            navigationIcon = {
                IconButton(onClick = { onEvent(EditViewEvent.Back) }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        )
        
        TextField(
            value = state.subscription.name,
            onValueChange = { },
            modifier = Modifier.weight(1F).fillMaxWidth().padding(8.dp),
            label = { Text("Todo text") },
        )
    }
}