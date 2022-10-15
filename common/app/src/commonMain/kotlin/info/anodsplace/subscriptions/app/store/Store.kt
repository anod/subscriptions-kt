package info.anodsplace.subscriptions.app.store

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

interface StoreState
interface StoreEvent
interface StoreAction

interface Store<State : StoreState, Event : StoreEvent, Action : StoreAction> {
    val states: Flow<State>
    val state: State
    val actions: Flow<Action>
    fun handleEvent(event: Event)
}

abstract class BaseStore<State : StoreState, Event : StoreEvent, Action : StoreAction>(
    private val storeScope: CoroutineScope
) : Store<State, Event, Action> {
    private lateinit var _states: MutableStateFlow<State>
    override val states: Flow<State>
        get() = _states

    override var state: State
        get() {
            if (::_states.isInitialized) {
                return _states.value
            } else {
                throw Exception("\"_states\" was queried before being initialized")
            }
        }
        protected set(value) {
            if (!::_states.isInitialized) {
                _states = MutableStateFlow(value)
            } else {
                _states.update { value }
            }
        }

    private var _actions = Channel<Action>()
    override val actions: Flow<Action> = _actions.receiveAsFlow()

    protected fun emitAction(action: Action) {
        storeScope.launch {
            _actions.send(action)
        }
    }

    abstract override fun handleEvent(event: Event)
}