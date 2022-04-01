package info.anodsplace.subscriptions.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Checkbox
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import info.anodsplace.subscriptions.app.MainViewModel
import info.anodsplace.subscriptions.database.SubscriptionEntity

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val subscriptions by viewModel.subscriptions.collectAsState(emptyList())

    SideEffect {
        viewModel.reload()
    }

    Column {
        TopAppBar(
            title = { Text(text = "Subscriptions") },
            actions = {
                IconButton(onClick = {  }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null
                    )
                }
            }
        )

        Box(Modifier.weight(1F)) {
            SubscriptionsList(
                items = subscriptions,
                onItemClicked = viewModel::onItemClicked,
                onDoneChanged = viewModel::onItemDoneChanged,
                onDeleteItemClicked = viewModel::onItemDeleteClicked
            )
        }
    }
}

@Composable
private fun SubscriptionsList(
    items: List<SubscriptionEntity>,
    onItemClicked: (id: Long) -> Unit,
    onDoneChanged: (id: Long, isDone: Boolean) -> Unit,
    onDeleteItemClicked: (id: Long) -> Unit
) {
    Box {
        val listState = rememberLazyListState()

        LazyColumn(state = listState) {
            items(items.size) {
                Item(
                    item = items[it],
                    onItemClicked = onItemClicked,
                    onDoneChanged = onDoneChanged,
                    onDeleteItemClicked = onDeleteItemClicked
                )

                Divider()
            }
        }

    }
}

@Composable
private fun Item(
    item: SubscriptionEntity,
    onItemClicked: (id: Long) -> Unit,
    onDoneChanged: (id: Long, isDone: Boolean) -> Unit,
    onDeleteItemClicked: (id: Long) -> Unit
) {
    Row(modifier = Modifier.clickable(onClick = { onItemClicked(item.id) })) {
        Spacer(modifier = Modifier.width(8.dp))

        Checkbox(
            checked = false,
            modifier = Modifier.align(Alignment.CenterVertically),
            onCheckedChange = { onDoneChanged(item.id, it) }
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = AnnotatedString(item.name),
            modifier = Modifier.weight(1F).align(Alignment.CenterVertically),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(onClick = { onDeleteItemClicked(item.id) }) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = null
            )
        }
    }
}