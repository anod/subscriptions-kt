package info.anodsplace.subscriptions.app

import info.anodsplace.subscriptions.app.store.Store
import info.anodsplace.subscriptions.app.store.StoreAction
import info.anodsplace.subscriptions.app.store.StoreEvent
import info.anodsplace.subscriptions.app.store.StoreState
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

interface ViewModel<State : StoreState, Event : StoreEvent, Action : StoreAction> : Store<State, Event, Action> {
    val viewModelScope: ViewModelScope
}
