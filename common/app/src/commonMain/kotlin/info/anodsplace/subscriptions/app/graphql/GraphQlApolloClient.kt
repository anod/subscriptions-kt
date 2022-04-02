package info.anodsplace.subscriptions.app.graphql

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloRequest
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Operation
import com.apollographql.apollo3.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo3.cache.normalized.normalizedCache
import com.apollographql.apollo3.interceptor.ApolloInterceptor
import com.apollographql.apollo3.interceptor.ApolloInterceptorChain
import info.anodsplace.subscriptions.graphql.GetPaymentsQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.logger.Logger

class GraphQlApolloClient(
    private val tokenInterceptor: TokenInterceptor,
    private val apolloClient: ApolloClient,
    private val logger: Logger
) : GraphQLClient {

    companion object {
        private fun createApolloClient(tokenInterceptor: TokenInterceptor): ApolloClient = ApolloClient.Builder()
            .serverUrl("http://localhost:8080/v1/graphql")
            .addInterceptor(tokenInterceptor)
            .normalizedCache(MemoryCacheFactory(maxSizeBytes = 100 * 1024 * 1024))
            .build()
    }

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

    constructor(logger: Logger) : this(TokenInterceptor(), logger)

    constructor(tokenInterceptor: TokenInterceptor, logger: Logger)
            : this(tokenInterceptor, createApolloClient(tokenInterceptor), logger)

    override fun observePayments(): Flow<List<GetPaymentsQuery.Payment>> {
        return apolloClient.query(GetPaymentsQuery()).toFlow().map { result ->
            if (result.hasErrors()) {
                val message = result.errors?.joinToString(";") { e -> e.message }
                logger.error("Cannot execute GetSubscriptionsQuery: $message")
                throw IllegalStateException(message)
            }
            result.data?.payment ?: emptyList()
        }
    }
}