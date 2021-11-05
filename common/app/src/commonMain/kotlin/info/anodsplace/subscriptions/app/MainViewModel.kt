package info.anodsplace.subscriptions.app

import info.anodsplace.subscriptions.app.store.SubscriptionsStore
import info.anodsplace.subscriptions.database.SubscriptionEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import org.koin.core.component.inject

interface MainViewModel : ViewModel {
    val subscriptions: Flow<List<SubscriptionEntity>>
    val text: String
    fun onItemClicked(id: Long)
    fun onItemDoneChanged(id: Long, isDone: Boolean)
    fun onItemDeleteClicked(id: Long)
    fun onAddItemClicked()
    fun onInputTextChanged(value: String)
}

class CommonMainViewModel(private val action: MutableSharedFlow<UiAction>, private val currentScope: CoroutineScope) :
    MainViewModel {
    private val store: SubscriptionsStore by inject()

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
}