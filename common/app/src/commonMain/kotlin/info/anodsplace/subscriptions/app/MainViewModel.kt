package info.anodsplace.subscriptions.app

import info.anodsplace.subscriptions.app.store.SubscriptionAction
import info.anodsplace.subscriptions.app.store.SubscriptionsStore
import info.anodsplace.subscriptions.database.SubscriptionEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface MainViewModel : ViewModel {
    val subscriptions: Flow<List<SubscriptionEntity>>
    val text: String
    fun onItemClicked(id: Long)
    fun onItemDoneChanged(id: Long, isDone: Boolean)
    fun onItemDeleteClicked(id: Long)
    fun onAddItemClicked()
    fun onInputTextChanged(value: String)
    fun reload()
}

class CommonMainViewModel(
    override val store: SubscriptionsStore,
    override val currentScope: CoroutineScope,
) : MainViewModel {

    override val subscriptions: Flow<List<SubscriptionEntity>>
        get() = store.subscriptions

    override val text: String = ""

    override fun onItemClicked(id: Long) {

    }

    override fun onItemDoneChanged(id: Long, isDone: Boolean) {

    }

    override fun onItemDeleteClicked(id: Long) {

    }

    override fun onAddItemClicked() {

    }

    override fun onInputTextChanged(value: String) {

    }

    override fun reload() {
        store.dispatch(SubscriptionAction.Refresh(forceLoad = true))
    }
}