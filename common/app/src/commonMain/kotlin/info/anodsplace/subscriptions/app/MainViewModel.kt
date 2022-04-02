package info.anodsplace.subscriptions.app

import info.anodsplace.subscriptions.app.store.SubscriptionsStore
import info.anodsplace.subscriptions.graphql.GetPaymentsQuery
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

interface MainViewModel : ViewModel {
    val subscriptions: Flow<List<GetPaymentsQuery.Payment>>
    val text: String
    fun onItemClicked(id: Long)
    fun onItemDoneChanged(id: Long, isDone: Boolean)
    fun onItemDeleteClicked(id: Long)
    fun onAddItemClicked()
    fun onInputTextChanged(value: String)
}

class CommonMainViewModel(
    override val store: SubscriptionsStore,
    override val currentScope: CoroutineScope,
) : MainViewModel {

    override val subscriptions: Flow<List<GetPaymentsQuery.Payment>>
        get() = store.subscriptions

    override val text: String = ""

    override fun onItemClicked(id: Long) {

    }

    override fun onItemDoneChanged(id: Long, isDone: Boolean) {

    }

    override fun onItemDeleteClicked(id: Long) {

    }

    override fun onAddItemClicked() {
        currentScope.launch {
            store.navigate(route = Route.Edit)
        }
    }

    override fun onInputTextChanged(value: String) {

    }
}