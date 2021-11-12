package info.anodsplace.subscriptions.app.graphql

import info.anodsplace.subscriptions.database.SubscriptionEntity

interface GraphQLClient {
    var token: String
    suspend fun loadSubscriptions(): List<SubscriptionEntity>
}