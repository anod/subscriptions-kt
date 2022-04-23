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
import info.anodsplace.subscriptions.app.EditViewModel
import info.anodsplace.subscriptions.app.ScreenLoadState
import info.anodsplace.subscriptions.app.store.Subscription

@Composable
fun EditScreen(viewModel: EditViewModel) {
    val screenState by viewModel.load().collectAsState(ScreenLoadState.Loading)
    if (screenState is ScreenLoadState.Ready<Subscription>) {
        EditSubscription((screenState as ScreenLoadState.Ready<Subscription>).value, viewModel)
    } else {
        CircularProgressIndicator()
    }
}

@Composable
fun EditSubscription(subscription: Subscription, viewModel: EditViewModel) {
    val titleText = if (viewModel.isEdit) "Edit ${subscription.name}" else "New subscription"
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TopAppBar(
            title = { Text(text = titleText) },
            navigationIcon = {
                IconButton(onClick = { viewModel.navigateBack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        )
        
        TextField(
            value = subscription.name,
            onValueChange = { },
            modifier = Modifier.weight(1F).fillMaxWidth().padding(8.dp),
            label = { Text("Todo text") },
        )
    }
}