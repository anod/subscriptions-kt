package info.anodsplace.subscriptions.web

import androidx.compose.runtime.Composable
import info.anodsplace.subscriptions.app.EditViewModel
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexDirection
import org.jetbrains.compose.web.css.FlexWrap
import org.jetbrains.compose.web.css.JustifyContent
import org.jetbrains.compose.web.css.display
import org.jetbrains.compose.web.css.flexFlow
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.justifyContent
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text

@Composable
fun EditUi(viewModel: EditViewModel) {

    Div(
        attrs = {
            style {
                width(100.percent)
                height(100.percent)
                display(DisplayStyle.Flex)
                flexFlow(FlexDirection.Column, FlexWrap.Nowrap)
            }
        }
    ) {
        Div(
            attrs = {
                style {
                    width(100.percent)
                    property("flex", "0 1 auto")
                }
            }
        ) {
            NavBar(
                title = "Edit todo",
                navigationIcon = NavBarIcon(
                    name = "arrow_back",
                    onClick = viewModel::onCloseClicked
                )
            )
        }

        Div(
            attrs = {
                style {
                    width(100.percent)
                    property("flex", "1 1 auto")
                    property("padding", "0px 16px 0px 16px")
                    display(DisplayStyle.Flex)
                    flexFlow(FlexDirection.Column, FlexWrap.Nowrap)
                }
            }
        ) {
            MaterialTextArea(
                id = "text_area_edit_todo",
                label = "",
                text = viewModel.text,
                onTextChanged = viewModel::onTextChanged,
                attrs = {
                    style {
                        width(100.percent)
                        property("flex", "1 1 auto")
                    }
                }
            )
        }

        Div(
            attrs = {
                style {
                    width(100.percent)
                    property("flex", "0 1 auto")
                    property("padding-bottom", "16px")
                    display(DisplayStyle.Flex)
                    justifyContent(JustifyContent.Center)
                }
            }
        ) {
            MaterialCheckbox(
                checked = false,
                onCheckedChange = viewModel::onDoneChanged,
                content = {
                    Text(value = "Completed")
                }
            )
        }
    }
}

