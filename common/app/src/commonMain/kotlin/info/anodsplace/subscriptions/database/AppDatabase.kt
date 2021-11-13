package info.anodsplace.subscriptions.database

import kotlinx.coroutines.flow.Flow

interface AppDatabase {

    fun observeAll(): Flow<List<SubscriptionEntity>>

    suspend fun select(id: Long): SubscriptionEntity?

    suspend fun upsert(entity: SubscriptionEntity): Long

    suspend fun upsert(entities: List<SubscriptionEntity>)

    suspend fun delete(id: Long)

    suspend fun clear()

    suspend fun loadSubscriptions(): List<SubscriptionEntity>
}
