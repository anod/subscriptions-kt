package info.anodsplace.subscriptions.app.graphql

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloRequest
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Operation
import com.apollographql.apollo3.interceptor.ApolloInterceptor
import com.apollographql.apollo3.interceptor.ApolloInterceptorChain
import info.anodsplace.subscriptions.database.SubscriptionEntity
import info.anodsplace.subscriptions.graphql.GetSubscriptionsQuery
import kotlinx.coroutines.flow.Flow

class GraphQlApolloClient(private val tokenInterceptor: TokenInterceptor, private val apolloClient: ApolloClient) : GraphQLClient {
    override var token: String
        get() = tokenInterceptor.token
        set(value) { tokenInterceptor.token = value }

    class TokenInterceptor : ApolloInterceptor {
        var token: String = ""

        override fun <D : Operation.Data> intercept(
            request: ApolloRequest<D>,
            chain: ApolloInterceptorChain
        ): Flow<ApolloResponse<D>> {
            val authenticated = request.newBuilder().addHttpHeader("Authorization", "Bearer $token").build()
            return chain.proceed(authenticated)
        }
    }

    constructor() : this(TokenInterceptor())

    constructor(tokenInterceptor: TokenInterceptor) : this(tokenInterceptor, ApolloClient.Builder()
        .serverUrl("http://localhost:8080/v1/graphql")
        .addInterceptor(tokenInterceptor)
        .build())

    override suspend fun loadSubscriptions(): List<SubscriptionEntity> {
        val response = apolloClient.query(GetSubscriptionsQuery()).execute()
        val list = response.data?.subscriptions ?: emptyList()
        return list.map {
            SubscriptionEntity(
                id = it.id.toLong(),
                objectId = it.objectId,
                name = it.name,
                link = it.link,
                created = 0,
                userId = it.userId.toLong()
            )
        }
    }

}