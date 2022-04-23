package info.anodsplace.subscriptions.app

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

class ViewModelScope(context: CoroutineContext) : CoroutineScope {
    constructor(dispatcher: CoroutineDispatcher = Dispatchers.Main)
            : this(SupervisorJob() + dispatcher)

    override val coroutineContext: CoroutineContext = context
}

interface ViewModel {
    val viewModelScope: ViewModelScope
}
