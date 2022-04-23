package info.anodsplace.subscriptions.app.graphql

import info.anodsplace.subscriptions.graphql.fragment.GQPayment
import info.anodsplace.subscriptions.graphql.fragment.GQUser
import kotlinx.coroutines.flow.Flow

interface GraphQLClient {
    var token: String
    fun observePayments(): Flow<List<GQPayment>>
    suspend fun loadUser(userId: Int): GQUser
    suspend fun loadPayment(id: Long): GQPayment
}