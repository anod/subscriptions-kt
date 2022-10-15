package info.anodsplace.subscriptions.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import info.anodsplace.subscriptions.app.MainViewEvent
import info.anodsplace.subscriptions.app.MainViewModel
import info.anodsplace.subscriptions.app.MainViewState
import info.anodsplace.subscriptions.app.store.Subscription

@Composable
fun MainScreen(state: MainViewState, onEvent: (MainViewEvent) -> Unit, formatPrice: (price: Float, currencyCode: String) -> String) {

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
                items = state.subscriptions,
                onEvent = onEvent,
                formatPrice = formatPrice
            )

        }

        Text(
            text = "Total: ${formatPrice(state.total, state.userCurrency)}",
            modifier = Modifier.padding(16.dp).align(Alignment.End)
        )
    }
}

@Composable
private fun SubscriptionsList(
    items: List<Subscription>,
    onEvent: (MainViewEvent) -> Unit,
    formatPrice: (price: Float, currencyCode: String) -> String
) {
    Box {
        val listState = rememberLazyListState()

        LazyColumn(state = listState) {
            items(items.size) {
                Item(
                    item = items[it],
                    onEvent = onEvent,
                    formatPrice = formatPrice
                )

                Divider()
            }
        }

    }
}

@Composable
private fun Item(
    item: Subscription,
    onEvent: (MainViewEvent) -> Unit,
    formatPrice: (price: Float, currencyCode: String) -> String
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable(onClick = { onEvent(MainViewEvent.ItemClicked(item.id.toLong())) })
    ) {

        Column(
            modifier = Modifier
                .padding(all = 16.dp)
                .weight(1f)
                .align(Alignment.CenterVertically),
        ) {
            Text(
                text = AnnotatedString(item.name),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = AnnotatedString(item.method),
                modifier = Modifier.padding(top = 4.dp),
                style = MaterialTheme.typography.caption,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        if (item.isConverted) {
            Column(
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 16.dp, end = 16.dp)
                    .defaultMinSize(minWidth = 56.dp)
                    .align(Alignment.CenterVertically),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = AnnotatedString(formatPrice(item.price, item.currency)),
                    maxLines = 1,
                )
                Text(
                    text = formatPrice(item.originalPrice, item.originalCurrency),
                    modifier = Modifier.padding(top = 4.dp),
                    style = MaterialTheme.typography.caption,
                    maxLines = 1,
                )
            }
        } else {
            Text(
                text = AnnotatedString(formatPrice(item.price, item.currency)),
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 16.dp, end = 16.dp)
                    .align(Alignment.CenterVertically),
                maxLines = 1,
            )
        }
    }
}
