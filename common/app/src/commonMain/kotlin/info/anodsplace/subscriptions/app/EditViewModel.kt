package info.anodsplace.subscriptions.app

import info.anodsplace.subscriptions.app.currency.ExchangeRate
import info.anodsplace.subscriptions.app.graphql.GraphQLClient
import info.anodsplace.subscriptions.app.store.*
import info.anodsplace.subscriptions.graphql.fragment.GQUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import org.koin.core.logger.Logger

data class EditViewState(
    val userCurrency: String = "",
    val subscriptionId: Long = 0L,
    val screenState: ScreenState = ScreenState.Initial,
    val subscription: Subscription = Subscription.empty
) : StoreState {
    val isEditMode: Boolean = subscriptionId > 0
}

sealed interface EditViewEvent : StoreEvent {
    object Back : EditViewEvent
}

sealed interface EditViewAction : StoreAction

interface EditViewModel : ViewModel<EditViewState, EditViewEvent, EditViewAction>

class CommonEditViewModel(
    id: Long,
    override val viewModelScope: ViewModelScope,
    private val store: SubscriptionsStore,
    private val graphQLClient: GraphQLClient,
    private val exchangeRate: ExchangeRate,
    private val logger: Logger
) : EditViewModel, BaseStore<EditViewState, EditViewEvent, EditViewAction>(storeScope = viewModelScope) {

    init {
        state = EditViewState(
            subscriptionId = id,
            screenState = if (id > 0) ScreenState.Loading else ScreenState.Ready,
            userCurrency = store.state.user.currency
        )

        if (id > 0) {
            viewModelScope.launch {
                state = try {
                    val sub = loadSubscription(id)
                    state.copy(subscription = sub, screenState = ScreenState.Ready)
                } catch (e: Exception) {
                    logger.error("Cannot load sub $e")
                    state.copy(screenState = ScreenState.Error("Cannot load subscription"))
                }
            }
        }
    }

    private suspend fun loadSubscription(id: Long): Subscription {
        val sub = graphQLClient.loadPayment(id).let { p ->
            Subscription(
                payment = p,
                price = if (p.currency == state.userCurrency) p.price else exchangeRate.convert(p.price, p.currency),
                currency = state.userCurrency
            )
        }
        return sub
    }

    override fun handleEvent(event: EditViewEvent) {
        when (event) {
            EditViewEvent.Back -> store.handleEvent(SubscriptionEvent.Navigate(route = Route.Main()))
        }
    }

}