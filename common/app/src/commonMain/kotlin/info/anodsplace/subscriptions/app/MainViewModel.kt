package info.anodsplace.subscriptions.app

import info.anodsplace.subscriptions.app.currency.Currency
import info.anodsplace.subscriptions.app.currency.ExchangeRate
import info.anodsplace.subscriptions.app.graphql.GraphQLClient
import info.anodsplace.subscriptions.app.store.Subscription
import info.anodsplace.subscriptions.app.store.SubscriptionsStore
import info.anodsplace.subscriptions.graphql.fragment.GQUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

interface MainViewModel : ViewModel {
    val user: GQUser
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

class CommonMainViewModel(
    private val store: SubscriptionsStore,
    override val viewModelScope: ViewModelScope,
    private val exchangeRate: ExchangeRate,
    private val currency: Currency,
    private val graphQLClient: GraphQLClient
) : MainViewModel {

    override val user: GQUser
        get() = store.user

    override val subscriptions: Flow<List<Subscription>>
        get() = graphQLClient.observePayments().map { list ->
            list.map { p ->
                Subscription(
                    payment = p,
                    price = if (p.currency == user.currency) p.price else exchangeRate.convert(p.price, p.currency),
                    currency = user.currency
                )
            }
        }

    override val total: Flow<Float>
        get() = subscriptions.map {
            it.map { p -> exchangeRate.convert(p.price, p.currency) }.sum()
        }

    override val text: String = ""

    override fun formatPrice(price: Float, currencyCode: String): String {
        return currency.format(price, currencyCode)
    }

    override fun onItemClicked(id: Long) {
        store.navigate(Route.Edit(id = id))
    }

    override fun onItemDoneChanged(id: Long, isDone: Boolean) {

    }

    override fun onItemDeleteClicked(id: Long) {

    }

    override fun onAddItemClicked() {
        viewModelScope.launch {
            store.navigate(route = Route.Edit(id = 0))
        }
    }

    override fun onInputTextChanged(value: String) {

    }
}