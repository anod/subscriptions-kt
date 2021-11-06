package info.anodsplace.subscriptions.app.store

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface State
interface Action
interface Effect

interface Store<S : State, A : Action, E : Effect> {
    val state: StateFlow<S>
    val sideEffect: Flow<E>
    fun dispatch(action: A)
}