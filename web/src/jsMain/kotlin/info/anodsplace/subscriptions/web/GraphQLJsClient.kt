package info.anodsplace.subscriptions.web

import info.anodsplace.subscriptions.app.graphql.*
import info.anodsplace.subscriptions.database.SubscriptionEntity
import info.anodsplace.subscriptions.single
import info.anodsplace.subscriptions.web.apolloclient.*
import kotlin.js.Date

fun Subscription.toEntity(): SubscriptionEntity = SubscriptionEntity(
    id = id.toLong(),
    objectId = objectId,
    name = name,
    link = link,
    created = Date(created).getTime().toLong(),
    userId = userId.toLong()
)

class GraphQLJsClient: GraphQLClient {
    private val client: ApolloClientModule.ApolloClient = createApolloClient(url = "http://localhost:8080/v1/graphql")
    override var token: String = ""

    override suspend fun loadSubscriptions(): List<SubscriptionEntity> {
        val query = ApolloClientModule.gql("""
            query GetSubscriptions {
              subscriptions {
                id
                objectId
                created
                link
                name
                userId
              }
            }
        """.trimIndent())
        val result = client.query<ApolloClientModule.QueryResult<GetSubscriptions>>(QueryOptions(query, token)).single()
        val subscriptions = result.data.subscriptions.iterator()
        val entities = mutableListOf<SubscriptionEntity>()
        for (sub in subscriptions) {
            val entity = sub.unsafeCast<Subscription>().toEntity()
            entities.add(entity)
        }
        return entities
    }
}