package info.anodsplace.subscriptions.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface

import info.anodsplace.subscriptions.ui.root.TodoRootContent
import info.anodsplace.subscriptions.app.CommonRootViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root = CommonRootViewModel()

        setContent {
            ComposeAppTheme {
                Surface(color = MaterialTheme.colors.background) {
                    TodoRootContent(root)
                }
            }
        }
    }
}
