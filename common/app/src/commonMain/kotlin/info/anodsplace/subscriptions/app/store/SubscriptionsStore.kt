package info.anodsplace.subscriptions.app.store

import info.anodsplace.subscriptions.app.AppCoroutineScope
import info.anodsplace.subscriptions.database.AppDatabase
import info.anodsplace.subscriptions.database.SubscriptionEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class SubscriptionsState(
    val progress: Boolean = false,
    val graphQlToken: String = "",
    val selected: SubscriptionEntity? = null
) : State

sealed class SubscriptionAction : Action {
    data class Refresh(val forceLoad: Boolean) : SubscriptionAction()
    data class Add(val subscription: SubscriptionEntity) : SubscriptionAction()
    data class Delete(val id: Long) : SubscriptionAction()
    data class SelectFeed(val subscription: SubscriptionEntity?) : SubscriptionAction()
    data class Data(val subscription: List<SubscriptionEntity>) : SubscriptionAction()
    data class Error(val error: Exception) : SubscriptionAction()
}

sealed class SubscriptionSideEffect : Effect {
    data class Error(val error: Exception) : SubscriptionSideEffect()
}

interface SubscriptionsStore : Store<SubscriptionsState, SubscriptionAction, SubscriptionSideEffect> {
    val subscriptions: Flow<List<SubscriptionEntity>>
}

class DefaultSubscriptionsStore(
    private val appDatabase: AppDatabase,
    private val appScope: AppCoroutineScope
) : SubscriptionsStore {
    private val state = MutableStateFlow(SubscriptionsState())
    private val sideEffect = MutableSharedFlow<SubscriptionSideEffect>()

    override val subscriptions: Flow<List<SubscriptionEntity>>
        get() = appDatabase.observeAll()

    override fun observeState(): StateFlow<SubscriptionsState> = state

    override fun observeSideEffect(): Flow<SubscriptionSideEffect> = sideEffect

    override fun dispatch(action: SubscriptionAction) {
        // Napier.d(tag = "FeedStore", message = "Action: $action")
        val oldState = state.value

        val newState = when (action) {
            is SubscriptionAction.Refresh -> {
                if (oldState.progress) {
                    appScope.launch { sideEffect.emit(SubscriptionSideEffect.Error(Exception("In progress"))) }
                    oldState
                } else {
                    appScope.launch { loadAllFeeds() }
                    oldState.copy(progress = true)
                }
            }
            is SubscriptionAction.Add -> {
                if (oldState.progress) {
                    appScope.launch { sideEffect.emit(SubscriptionSideEffect.Error(Exception("In progress"))) }
                    oldState
                } else {
                    appScope.launch { addSubscription(action.subscription) }
                    SubscriptionsState(progress = true)
                }
            }
            is SubscriptionAction.Delete -> {
                if (oldState.progress) {
                    appScope.launch { sideEffect.emit(SubscriptionSideEffect.Error(Exception("In progress"))) }
                    oldState
                } else {
                    appScope.launch { deleteFeed(action.id) }
                    SubscriptionsState(progress = true)
                }
            }
            is SubscriptionAction.SelectFeed -> {
//                if (action.subscription == null || db.contains(action.subscription)) {
//                    oldState.copy(selected = action.subscription)
//                } else {
//                    appScope.launch { sideEffect.emit(SubscriptionSideEffect.Error(Exception("Unknown feed"))) }
//                    oldState
//                }
                appScope.launch { sideEffect.emit(SubscriptionSideEffect.Error(Exception("Unknown feed"))) }
                oldState
            }
            is SubscriptionAction.Data -> {
                if (oldState.progress) {
                    val selected = oldState.selected?.let {
                        if (action.subscription.contains(it)) it else null
                    }
                    SubscriptionsState(progress = false, selected = selected)
                } else {
                    appScope.launch { sideEffect.emit(SubscriptionSideEffect.Error(Exception("Unexpected action"))) }
                    oldState
                }
            }
            is SubscriptionAction.Error -> {
                if (oldState.progress) {
                    appScope.launch { sideEffect.emit(SubscriptionSideEffect.Error(action.error)) }
                    SubscriptionsState(progress = false)
                } else {
                    appScope.launch { sideEffect.emit(SubscriptionSideEffect.Error(Exception("Unexpected action"))) }
                    oldState
                }
            }
        }

        if (newState != oldState) {
            // Napier.d(tag = "FeedStore", message = "NewState: $newState")
            state.value = newState
        }
    }

    private suspend fun loadAllFeeds() {
        try {
            val allFeeds = appDatabase.loadSubscriptions()
            dispatch(SubscriptionAction.Data(allFeeds))
        } catch (e: Exception) {
            dispatch(SubscriptionAction.Error(e))
        }
    }

    private suspend fun addSubscription(subscription: SubscriptionEntity) {
        try {
            appDatabase.upsert(subscription)
            val allFeeds = appDatabase.loadSubscriptions()
            dispatch(SubscriptionAction.Data(allFeeds))
        } catch (e: Exception) {
            dispatch(SubscriptionAction.Error(e))
        }
    }

    private suspend fun deleteFeed(id: Long) {
        try {
            appDatabase.delete(id)
            val allFeeds = appDatabase.loadSubscriptions()
            dispatch(SubscriptionAction.Data(allFeeds))
        } catch (e: Exception) {
            dispatch(SubscriptionAction.Error(e))
        }
    }
}