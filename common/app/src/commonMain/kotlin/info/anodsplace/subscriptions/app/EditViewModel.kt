package info.anodsplace.subscriptions.app

import info.anodsplace.subscriptions.app.currency.ExchangeRate
import info.anodsplace.subscriptions.app.graphql.GraphQLClient
import info.anodsplace.subscriptions.app.store.Subscription
import info.anodsplace.subscriptions.app.store.SubscriptionsStore
import info.anodsplace.subscriptions.graphql.fragment.GQUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.logger.Logger

interface EditViewModel : ViewModel {
    val isEdit: Boolean
    val user: GQUser
    fun load(): Flow<ScreenLoadState<Subscription>>
    fun navigateBack()
}

sealed class ScreenLoadState<out T : Any> {
    object Loading : ScreenLoadState<Nothing>()
    data class Ready<out T : Any>(val value: T) : ScreenLoadState<T>()
    data class Error(val message: String, val cause: Exception? = null) : ScreenLoadState<Nothing>()
}

class CommonEditViewModel(
    private val id: Long,
    override val viewModelScope: ViewModelScope,
    private val store: SubscriptionsStore,
    private val graphQLClient: GraphQLClient,
    private val exchangeRate: ExchangeRate,
    private val logger: Logger
) : EditViewModel {
    override val isEdit: Boolean = id > 0

    override val user: GQUser
        get() = store.user

    override fun load(): Flow<ScreenLoadState<Subscription>> = flow {
        try {
            if (id > 0) {
                val sub = graphQLClient.loadPayment(id).let { p ->
                    Subscription(
                        payment = p,
                        price = if (p.currency == user.currency) p.price else exchangeRate.convert(p.price, p.currency),
                        currency = user.currency
                    )
                }
                emit(ScreenLoadState.Ready(sub))
            } else {
                emit(ScreenLoadState.Ready(Subscription.empty))
            }
        } catch (e: Exception) {
            logger.error("Cannot load sub $e")
            emit(ScreenLoadState.Error("Cannot load sub", e))
        }
    }

    override fun navigateBack() {
        store.navigate(Route.Main())
    }

}