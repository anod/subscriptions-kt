package info.anodsplace.subscriptions.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import info.anodsplace.subscriptions.app.EditViewModel

@Composable
fun EditScreen(viewModel: EditViewModel) {

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TopAppBar(
            title = { Text("Edit todo") },
            navigationIcon = {
                IconButton(onClick = {  }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        )

        TextField(
            value = "",
            onValueChange = {  },
            modifier = Modifier.weight(1F).fillMaxWidth().padding(8.dp),
            label = { Text("Todo text") },
        )

        Row(modifier = Modifier.padding(8.dp)) {
            Text(text = "Completed")

            Spacer(modifier = Modifier.width(8.dp))

            Checkbox(
                checked = false,
                onCheckedChange = { }
            )
        }
    }
}
