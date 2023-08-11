package info.anodsplace.subscriptions.app.store

import info.anodsplace.subscriptions.app.AppCoroutineScope
import info.anodsplace.subscriptions.app.Config
import info.anodsplace.subscriptions.app.Route
import info.anodsplace.subscriptions.app.graphql.GraphQLClient
import info.anodsplace.subscriptions.graphql.fragment.GQPayment
import info.anodsplace.subscriptions.graphql.fragment.GQUser
import info.anodsplace.subscriptions.server.contract.LoginRequest
import info.anodsplace.subscriptions.server.contract.LoginResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.launch
import org.koin.core.logger.Logger

class EmptyUser(
    override val currency: String = "",
    override val email: String = "",
    override val id: Int = 0,
    override val name: String = "",
    override val objectId: String = ""
) : GQUser

data class SubscriptionsState(
    val user: GQUser = EmptyUser(),
    val progress: Boolean = false,
    val graphQlToken: String = "",
) : StoreState {
    val isLoggedIn: Boolean
        get() = graphQlToken.isNotEmpty() && user.id > 0
}

sealed class SubscriptionEvent : StoreEvent {
    data class Login(val username: String, val password: String) : SubscriptionEvent()
    data class LoggedIn(val username: String, val graphQlToken: String, val user: GQUser) :
        SubscriptionEvent()

    data class Add(val subscription: GQPayment) : SubscriptionEvent()
    data class Delete(val id: Long) : SubscriptionEvent()
    data class SelectFeed(val subscription: GQPayment?) : SubscriptionEvent()
    data class Data(val subscription: List<GQPayment>) : SubscriptionEvent()
    data class Error(val error: Exception) : SubscriptionEvent()
    data class Navigate(val route: Route) : SubscriptionEvent()
}

sealed class SubscriptionAction : StoreAction {
    data class Error(val error: Exception) : SubscriptionAction()
    data class Navigate(val route: Route) : SubscriptionAction()
}

interface SubscriptionsStore : Store<SubscriptionsState, SubscriptionEvent, SubscriptionAction>

class DefaultSubscriptionsStore(
    private val appScope: AppCoroutineScope,
    private val httpClient: HttpClient,
    private val graphQLClient: GraphQLClient,
    private val logger: Logger,
    private val config: Config
) : BaseStore<SubscriptionsState, SubscriptionEvent, SubscriptionAction>(storeScope = appScope), SubscriptionsStore {

    init {
        state = SubscriptionsState()
    }

    override fun handleEvent(event: SubscriptionEvent) {
        val oldState = state

        val newState = when (event) {
            is SubscriptionEvent.Add -> {
                if (oldState.progress) {
                    emitAction(SubscriptionAction.Error(Exception("In progress")))
                    oldState
                } else {
                    SubscriptionsState(progress = true)
                }
            }
            is SubscriptionEvent.Delete -> {
                if (oldState.progress) {
                    emitAction(SubscriptionAction.Error(Exception("In progress")))
                    oldState
                } else {
                    SubscriptionsState(progress = true)
                }
            }
            is SubscriptionEvent.SelectFeed -> {
                emitAction(SubscriptionAction.Error(Exception("Unknown feed")))
                oldState
            }
            is SubscriptionEvent.Data -> {
                if (oldState.progress) {
                    SubscriptionsState(progress = false)
                } else {
                    emitAction(SubscriptionAction.Error(Exception("Unexpected action")))
                    oldState
                }
            }
            is SubscriptionEvent.Error -> {
                if (oldState.progress) {
                    emitAction(SubscriptionAction.Error(event.error))
                    SubscriptionsState(progress = false)
                } else {
                    emitAction(SubscriptionAction.Error(Exception("Unexpected action")))
                    oldState
                }
            }
            is SubscriptionEvent.Login -> {
                appScope.launch { login(event) }
                oldState.copy(progress = true)
            }
            is SubscriptionEvent.LoggedIn -> {
                emitAction(SubscriptionAction.Navigate(Route.Main()))
                oldState.copy(progress = false, graphQlToken = event.graphQlToken, user = event.user)
            }

            is SubscriptionEvent.Navigate -> {
                emitAction(SubscriptionAction.Navigate(event.route))
                oldState
            }
        }

        if (newState != oldState) {
            state = newState
        }
    }

    private suspend fun login(action: SubscriptionEvent.Login) {
        try {
            val response: LoginResponse = httpClient.post {
                 url("${config.serverEndpoint}/login")
                 contentType(ContentType.Application.Json)
                 setBody(LoginRequest(username = action.username, password = action.password))
            }.body()
            graphQLClient.token = response.token
            val user = graphQLClient.loadUser(response.userId)
            handleEvent(SubscriptionEvent.LoggedIn(username = action.username, graphQlToken = response.token, user = user))
        } catch (e: Exception) {
            logger.error("login: ${e.message}")
            handleEvent(SubscriptionEvent.Error(e))
        }
    }
}