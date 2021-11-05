package info.anodsplace.subscriptions.app

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow

sealed interface Child {
    class Main(val viewModel: MainViewModel) : Child
    class Edit(val viewModel: EditViewModel) : Child
}

interface RootViewModel: ViewModel {
    val instance: Child
}

class CommonRootViewModel() : RootViewModel {
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