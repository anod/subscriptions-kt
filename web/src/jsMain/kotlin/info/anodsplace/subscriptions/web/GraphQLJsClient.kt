package info.anodsplace.subscriptions.web

import info.anodsplace.subscriptions.app.graphql.*
import info.anodsplace.subscriptions.database.SubscriptionEntity
import info.anodsplace.subscriptions.single
import info.anodsplace.subscriptions.web.apolloclient.*

class GraphQLJsClient: GraphQLClient {
    private val client: ApolloClientModule.ApolloClient = createApolloClient(url = "http://localhost:8080/v1/graphql")
    override var token: String = ""

    override suspend fun loadSubscriptions(): List<SubscriptionEntity> {
        val query = ApolloClientModule.gql("""
            query GetSubscriptions {
              subscriptions {
                created
                id
                link
                name
                objectId
              }
            }
        """.trimIndent())
        return client.query<List<SubscriptionEntity>>(QueryOptions(query, token)).single()
    }
}