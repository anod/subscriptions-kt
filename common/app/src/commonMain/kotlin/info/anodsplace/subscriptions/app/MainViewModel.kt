package info.anodsplace.subscriptions.app

import info.anodsplace.subscriptions.app.currency.Currency
import info.anodsplace.subscriptions.app.currency.ExchangeRate
import info.anodsplace.subscriptions.app.graphql.GraphQLClient
import info.anodsplace.subscriptions.app.store.*
import info.anodsplace.subscriptions.graphql.fragment.GQUser
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

data class MainViewState(
    val userCurrency: String = "",
    val subscriptions: List<Subscription> = emptyList(),
    val total: Float = 0.0f,
    val text: String = ""
) : StoreState

sealed interface MainViewEvent : StoreEvent {
    class ItemClicked(val id: Long) : MainViewEvent
    class ItemDoneChanged(val id: Long, isDone: Boolean) : MainViewEvent
    class ItemDeleteClicked(val id: Long) : MainViewEvent
    object AddItemClicked : MainViewEvent
    class InputTextChanged(val value: String) : MainViewEvent

}
sealed interface MainViewAction : StoreAction

interface MainViewModel : ViewModel<MainViewState, MainViewEvent, MainViewAction> {
    fun formatPrice(price: Float, currencyCode: String): String
}

class CommonMainViewModel(
    private val store: SubscriptionsStore,
    override val viewModelScope: ViewModelScope,
    private val exchangeRate: ExchangeRate,
    private val currency: Currency,
    private val graphQLClient: GraphQLClient
) : MainViewModel, BaseStore<MainViewState, MainViewEvent, MainViewAction>(storeScope = viewModelScope) {

    init {
        state = MainViewState(
            userCurrency = store.state.user.currency
        )

        viewModelScope.launch {
            // todo: handle userCurrency change
            graphQLClient.observePayments().map { list ->
                list.map { p ->
                    Subscription(
                        payment = p,
                        price = if (p.currency == state.userCurrency) p.price else exchangeRate.convert(p.price, p.currency),
                        currency = state.userCurrency
                    )
                }
            }.collect {
                val total = it.map { p -> exchangeRate.convert(p.price, p.currency) }.sum()
                state = state.copy(subscriptions = it, total = total)
            }
        }

        viewModelScope.launch {
            store.states.map { it.user.currency }.distinctUntilChanged().collect { userCurrency ->
                state = state.copy(userCurrency = userCurrency)
            }
        }
    }


    override fun formatPrice(price: Float, currencyCode: String): String {
        return currency.format(price, currencyCode)
    }

    override fun handleEvent(event: MainViewEvent) {
        when (event) {
            MainViewEvent.AddItemClicked -> {
                store.handleEvent(SubscriptionEvent.Navigate(route = Route.Edit(id = 0)))
            }
            is MainViewEvent.InputTextChanged -> {

            }
            is MainViewEvent.ItemClicked -> {
                store.handleEvent(SubscriptionEvent.Navigate(route = Route.Edit(id = event.id)))
            }
            is MainViewEvent.ItemDeleteClicked -> {

            }
            is MainViewEvent.ItemDoneChanged -> {

            }
        }
    }
}