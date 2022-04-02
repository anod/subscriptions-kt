package info.anodsplace.subscriptions.app

import info.anodsplace.subscriptions.app.store.SubscriptionsStore
import info.anodsplace.subscriptions.graphql.GetPaymentsQuery
import info.anodsplace.subscriptions.graphql.GetUserQuery
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch

interface MainViewModel : ViewModel {
    val user: GetUserQuery.User
    val subscriptions: Flow<List<GetPaymentsQuery.Payment>>
    val total: Flow<Float>
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
    private val exchange = mapOf(
        "ILS" to 1f,
        "USD" to 3.20f
    )

    override val user: GetUserQuery.User
        get() = store.user

    override val subscriptions: Flow<List<GetPaymentsQuery.Payment>>
        get() = store.subscriptions

    override val total: Flow<Float>
        get() = store.subscriptions.map {
           it.map { p -> calcFinal(p.price, p.currency) }.sum()
        }

    private fun calcFinal(price: Float, currency: String): Float {
        val rate = exchange[currency] ?: throw IllegalStateException("No exchange rate for $currency")
        return price * rate
    }

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