package info.anodsplace.subscriptions.app.graphql

import info.anodsplace.subscriptions.graphql.GetPaymentsQuery
import info.anodsplace.subscriptions.graphql.GetUserQuery
import kotlinx.coroutines.flow.Flow

interface GraphQLClient {
    var token: String
    fun observePayments(): Flow<List<GetPaymentsQuery.Payment>>
    suspend fun loadUser(userId: Int): GetUserQuery.User
}