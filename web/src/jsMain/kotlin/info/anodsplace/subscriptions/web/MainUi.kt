package info.anodsplace.subscriptions.web

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import info.anodsplace.subscriptions.app.MainViewModel
import info.anodsplace.subscriptions.app.Subscription
import info.anodsplace.subscriptions.graphql.GetPaymentsQuery
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexDirection
import org.jetbrains.compose.web.css.FlexWrap
import org.jetbrains.compose.web.css.alignItems
import org.jetbrains.compose.web.css.display
import org.jetbrains.compose.web.css.flexFlow
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.margin
import org.jetbrains.compose.web.css.marginLeft
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.DOMScope
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Li
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.dom.Ul
import org.w3c.dom.HTMLUListElement

@Composable
fun MainUi(viewModel: MainViewModel) {
    val items by viewModel.subscriptions.collectAsState(emptyList())

    NavBar(title = "Subscriptions", icons = listOf(NavBarIcon(name = "add", onClick = viewModel::onAddItemClicked)))

    Ul(
        attrs = {
            style {
            }
        }
    ) {
        items.forEach { item ->
            Item(
                item = item,
                onClicked = viewModel::onItemClicked,
                onDoneChanged = viewModel::onItemDoneChanged,
                onDeleteClicked = viewModel::onItemDeleteClicked
            )
        }
    }
}

@Composable
private fun DOMScope<HTMLUListElement>.Item(
    item: Subscription,
    onClicked: (id: Long) -> Unit,
    onDoneChanged: (id: Long, isDone: Boolean) -> Unit,
    onDeleteClicked: (id: Long) -> Unit
) {
    Li(
        attrs = {
            style {
                width(100.percent)
                display(DisplayStyle.Flex)
                flexFlow(FlexDirection.Row, FlexWrap.Nowrap)
                alignItems(AlignItems.Center)
                property("color", "white")
                property("padding", "0px 0px 0px 16px")
            }
        }
    ) {
        MaterialCheckbox(
            checked = false,
            onCheckedChange = { onDoneChanged(item.id.toLong(), !false) },
            attrs = {
                style {
                    property("flex", "0 1 auto")
                    property("padding-top", 10.px) // Fix for the checkbox not being centered vertically
                }
            }
        )

        Div(
            attrs = {
                style {
                    height(48.px)
                    property("flex", "1 1 auto")
                    property("white-space", "nowrap")
                    property("text-overflow", "ellipsis")
                    property("overflow", "hidden")
                    display(DisplayStyle.Flex)
                    alignItems(AlignItems.Center)
                }
                onClick { onClicked(item.id.toLong()) }
            }
        ) {
            Text(value = item.name)
        }

        ImageButton(
            onClick = { onDeleteClicked(item.id.toLong()) },
            iconName = "delete",
            attrs = {
                style {
                    property("flex", "0 1 auto")
                    marginLeft(8.px)
                }
            }
        )
    }
}

@Composable
private fun TodoInput(
    text: String,
    onTextChanged: (String) -> Unit,
    onAddClicked: () -> Unit
) {
    Div(
        attrs = {
            style {
                width(100.percent)
                display(DisplayStyle.Flex)
                flexFlow(FlexDirection.Row, FlexWrap.Nowrap)
                alignItems(AlignItems.Center)
            }
        }
    ) {
        MaterialTextArea(
            id = "text_area_add_todo",
            label = "Add todo",
            text = text,
            onTextChanged = onTextChanged,
            attrs = {
                style {
                    property("flex", "1 1 auto")
                    margin(16.px)
                }
            }
        )

        ImageButton(
            onClick = onAddClicked,
            iconName = "add",
            attrs = {
                style {
                    property("flex", "0 1 auto")
                    property("margin", "0px 16px 0px 0px")
                }
            }
        )
    }
}
