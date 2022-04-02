package info.anodsplace.subscriptions.app.store

import info.anodsplace.subscriptions.app.AppCoroutineScope
import info.anodsplace.subscriptions.app.Route
import info.anodsplace.subscriptions.app.graphql.GraphQLClient
import info.anodsplace.subscriptions.graphql.GetPaymentsQuery
import info.anodsplace.subscriptions.graphql.GetUserQuery
import info.anodsplace.subscriptions.server.contract.LoginRequest
import info.anodsplace.subscriptions.server.contract.LoginResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.core.logger.Logger

data class SubscriptionsState(
    val user: GetUserQuery.User = GetUserQuery.User("", "", 0, "", ""),
    val progress: Boolean = false,
    val graphQlToken: String = "",
    val selected: GetPaymentsQuery.Payment? = null
) : State

sealed class SubscriptionAction : Action {
    data class Login(val username: String, val password: String) : SubscriptionAction()
    data class LoggedIn(val username: String, val graphQlToken: String, val user: GetUserQuery.User) : SubscriptionAction()
    data class Add(val subscription: GetPaymentsQuery.Payment) : SubscriptionAction()
    data class Delete(val id: Long) : SubscriptionAction()
    data class SelectFeed(val subscription: GetPaymentsQuery.Payment?) : SubscriptionAction()
    data class Data(val subscription: List<GetPaymentsQuery.Payment>) : SubscriptionAction()
    data class Error(val error: Exception) : SubscriptionAction()
}

sealed class SubscriptionSideEffect : Effect {
    data class Error(val error: Exception) : SubscriptionSideEffect()
    data class Action(val action: SubscriptionAction) : SubscriptionSideEffect()
    data class Navigate(val route: Route) : SubscriptionSideEffect()
}

interface SubscriptionsStore : Store<SubscriptionsState, SubscriptionAction, SubscriptionSideEffect> {
    val subscriptions: Flow<List<GetPaymentsQuery.Payment>>
    val isLoggedIn: Boolean
    val user: GetUserQuery.User
}

class DefaultSubscriptionsStore(
    private val appScope: AppCoroutineScope,
    private val httpClient: HttpClient,
    private val graphQLClient: GraphQLClient,
    private val logger: Logger
) : SubscriptionsStore {
    override val state = MutableStateFlow(SubscriptionsState())
    override val sideEffect = MutableSharedFlow<SubscriptionSideEffect>()

    override val subscriptions: Flow<List<GetPaymentsQuery.Payment>>
        get() = graphQLClient.observePayments()

    override val isLoggedIn: Boolean
        get() = state.value.graphQlToken.isNotEmpty() && user.id > 0

    override val user: GetUserQuery.User
        get() = state.value.user

    override fun dispatch(action: SubscriptionAction) {
        val oldState = state.value

        val newState = when (action) {
            is SubscriptionAction.Add -> {
                if (oldState.progress) {
                    appScope.launch { sideEffect.emit(SubscriptionSideEffect.Error(Exception("In progress"))) }
                    oldState
                } else {
                   // appScope.launch { addSubscription(action.subscription) }
                    SubscriptionsState(progress = true)
                }
            }
            is SubscriptionAction.Delete -> {
                if (oldState.progress) {
                    appScope.launch { sideEffect.emit(SubscriptionSideEffect.Error(Exception("In progress"))) }
                    oldState
                } else {
                    // appScope.launch { deleteFeed(action.id) }
                    SubscriptionsState(progress = true)
                }
            }
            is SubscriptionAction.SelectFeed -> {
                appScope.launch { sideEffect.emit(SubscriptionSideEffect.Error(Exception("Unknown feed"))) }
                oldState
            }
            is SubscriptionAction.Data -> {
                if (oldState.progress) {
                    val selected = oldState.selected?.let {
                        if (action.subscription.contains(it)) it else null
                    }
                    SubscriptionsState(progress = false, selected = selected)
                } else {
                    appScope.launch { sideEffect.emit(SubscriptionSideEffect.Error(Exception("Unexpected action"))) }
                    oldState
                }
            }
            is SubscriptionAction.Error -> {
                if (oldState.progress) {
                    appScope.launch { sideEffect.emit(SubscriptionSideEffect.Error(action.error)) }
                    SubscriptionsState(progress = false)
                } else {
                    appScope.launch { sideEffect.emit(SubscriptionSideEffect.Error(Exception("Unexpected action"))) }
                    oldState
                }
            }
            is SubscriptionAction.Login -> {
                appScope.launch { login(action) }
                oldState.copy(progress = true)
            }
            is SubscriptionAction.LoggedIn -> {
                navigate(Route.Main)
                oldState.copy(progress = false, graphQlToken = action.graphQlToken, user = action.user)
            }
        }

        if (newState != oldState) {
            state.value = newState
        }

        appScope.launch { sideEffect.emit(SubscriptionSideEffect.Action(action)) }
    }

    override fun navigate(route: Route) {
        appScope.launch { sideEffect.emit(SubscriptionSideEffect.Navigate(route)) }
    }

    private suspend fun login(action: SubscriptionAction.Login) {
        try {
            val response = httpClient.request<LoginResponse>("http://localhost:9090/login") {
                contentType(ContentType.Application.Json)
                method = HttpMethod.Post
                body = LoginRequest(username = action.username, password = action.password)
            }
            graphQLClient.token = response.token
            val user = graphQLClient.loadUser(response.userId)
            dispatch(SubscriptionAction.LoggedIn(username = action.username, graphQlToken = response.token, user = user))
        } catch (e: Exception) {
            logger.error("login: ${e.message}")
            dispatch(SubscriptionAction.Error(e))
        }
    }
}