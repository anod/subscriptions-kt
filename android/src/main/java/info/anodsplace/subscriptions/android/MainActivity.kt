package info.anodsplace.subscriptions.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import info.anodsplace.subscriptions.app.AppCoroutineScope

import info.anodsplace.subscriptions.ui.RootScreen
import info.anodsplace.subscriptions.app.CommonRouter
import info.anodsplace.subscriptions.ui.ComposeAppTheme
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainActivity : AppCompatActivity(), KoinComponent {
    private val appCoroutineScope: AppCoroutineScope by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root = CommonRouter(appCoroutineScope)

        setContent {
            ComposeAppTheme {
                Surface(color = MaterialTheme.colors.background) {
                    RootScreen(root)
                }
            }
        }
    }
}
