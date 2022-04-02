package info.anodsplace.subscriptions.app

import info.anodsplace.subscriptions.app.store.SubscriptionsStore
import info.anodsplace.subscriptions.graphql.GetPaymentsQuery
import info.anodsplace.subscriptions.graphql.GetUserQuery
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.core.component.inject

interface MainViewModel : ViewModel {
    val user: GetUserQuery.User
    val subscriptions: Flow<List<Subscription>>
    val total: Flow<Float>
    val text: String
    fun formatPrice(price: Float, currencyCode: String): String
    fun onItemClicked(id: Long)
    fun onItemDoneChanged(id: Long, isDone: Boolean)
    fun onItemDeleteClicked(id: Long)
    fun onAddItemClicked()
    fun onInputTextChanged(value: String)
}

class Subscription(
    private val payment: GetPaymentsQuery.Payment,
    val price: Float,
    val currency: String
) {
    val id: Int
        get() = payment.id
    val name: String
        get() = payment.subscription.name
    val method: String
        get() = payment.method.name
    val originalPrice: Float
        get() = payment.price
    val originalCurrency: String
        get() = payment.currency
    val isConverted: Boolean
        get() = payment.currency != currency
}

class CommonMainViewModel(
    override val store: SubscriptionsStore,
    override val currentScope: CoroutineScope,
) : MainViewModel {

    private val currency: Currency by inject()

    private val exchange = mapOf(
        "ILS" to 1f,
        "USD" to 3.20f
    )

    override val user: GetUserQuery.User
        get() = store.user

    override val subscriptions: Flow<List<Subscription>>
        get() = store.subscriptions.map { list ->
            list.map { p ->
                Subscription(
                    payment = p,
                    price = if (p.currency == user.currency) p.price else convertPrice(p.price, p.currency),
                    currency = user.currency
                )
            }
        }

    override val total: Flow<Float>
        get() = subscriptions.map {
           it.map { p -> convertPrice(p.price, p.currency) }.sum()
        }

    private fun convertPrice(price: Float, currency: String): Float {
        val rate = exchange[currency] ?: throw IllegalStateException("No exchange rate for $currency")
        return price * rate
    }

    override val text: String = ""

    override fun formatPrice(price: Float, currencyCode: String): String {
        return currency.format(price, currencyCode)
    }

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