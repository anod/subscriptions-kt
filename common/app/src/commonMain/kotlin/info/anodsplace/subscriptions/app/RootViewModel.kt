package info.anodsplace.subscriptions.app

import info.anodsplace.subscriptions.app.store.SubscriptionsStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import org.koin.core.component.inject

sealed interface Child {
    class Main(val viewModel: MainViewModel) : Child
    class Edit(val viewModel: EditViewModel) : Child
}

interface RootViewModel: ViewModel {
    val instance: Child
}

class CommonRootViewModel() : RootViewModel {
    private val store: SubscriptionsStore by inject()

    override val instance: Child = createMain()
    val action = MutableSharedFlow<UiAction>()

    private fun createMain(): Child {
        val mainViewModel = CommonMainViewModel(
            action,
            CoroutineScope(Dispatchers.Main + SupervisorJob())
        )
        return Child.Main(mainViewModel)
    }

}