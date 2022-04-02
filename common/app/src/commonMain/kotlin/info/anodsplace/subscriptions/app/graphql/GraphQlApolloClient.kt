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
import info.anodsplace.subscriptions.graphql.GetUserQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.logger.Logger

class GraphQlApolloClient(
    private val tokenInterceptor: TokenInterceptor,
    private val apolloClient: ApolloClient,
    private val logger: Logger
) : GraphQLClient {

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

    companion object {
        private fun createApolloClient(tokenInterceptor: TokenInterceptor): ApolloClient = ApolloClient.Builder()
            .serverUrl("http://localhost:8080/v1/graphql")
            .addInterceptor(tokenInterceptor)
            .normalizedCache(MemoryCacheFactory(maxSizeBytes = 100 * 1024 * 1024))
            .build()

        fun errorMessage(errors: List<com.apollographql.apollo3.api.Error>, query: String): String {
            val message = errors.joinToString(";") { e -> e.message }
            return "Cannot execute $query: $message"
        }
    }
    constructor(logger: Logger) : this(TokenInterceptor(), logger)

    constructor(tokenInterceptor: TokenInterceptor, logger: Logger)
            : this(tokenInterceptor, createApolloClient(tokenInterceptor), logger)

    override var token: String
        get() = tokenInterceptor.token
        set(value) { tokenInterceptor.token = value }

    override suspend fun loadUser(userId: Int): GetUserQuery.User {
        val result = apolloClient.query(GetUserQuery(userId = userId)).execute()
        if (result.hasErrors()) {
            val message = errorMessage(result.errors!!, "GetUserQuery")
            logger.error(message)
            throw IllegalStateException(message)
        }
        return result.data?.user ?: throw IllegalStateException("Cannot be null")
    }

    override fun observePayments(): Flow<List<GetPaymentsQuery.Payment>> {
        return apolloClient.query(GetPaymentsQuery()).toFlow().map { result ->
            if (result.hasErrors()) {
                val message = errorMessage(result.errors!!, "GetPaymentsQuery")
                logger.error(message)
                throw IllegalStateException(message)
            }
            result.data?.payment ?: emptyList()
        }
    }

}