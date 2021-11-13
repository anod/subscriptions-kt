package info.anodsplace.subscriptions.database

import com.squareup.sqldelight.EnumColumnAdapter
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import org.koin.core.logger.Level
import org.koin.core.logger.Logger

class DefaultAppDatabase(driver: SqlDriver) : AppDatabase {

    private val db = SubscriptionsDatabase(
        driver = driver,
        PeriodAdapter = Period.Adapter(
            periodAdapter = EnumColumnAdapter()
        )
    )

    override fun observeAll(): Flow<List<SubscriptionEntity>> =
        db.subscriptionQueries.selectAll().asFlow().mapToList()

    override suspend fun select(id: Long): SubscriptionEntity? = withContext(Dispatchers.Default) {
        return@withContext db.subscriptionQueries.select(id = id).executeAsOneOrNull()
    }

    override suspend fun upsert(entity: SubscriptionEntity): Long = withContext(Dispatchers.Default) {
        return@withContext db.transactionWithResult {
            return@transactionWithResult upsertEntity(entity, db)
        }
    }

    override suspend fun upsert(entities: List<SubscriptionEntity>): Unit = withContext(Dispatchers.Default) {
        db.transactionWithResult {
            for (entity in entities) {
                upsertEntity(entity, db)
            }
        }
    }

    override suspend fun delete(id: Long): Unit = withContext(Dispatchers.Default) {
        db.subscriptionQueries.delete(id = id)
    }

    override suspend fun clear(): Unit = withContext(Dispatchers.Default) {
        db.subscriptionQueries.clear()
    }

    override suspend fun loadSubscriptions(): List<SubscriptionEntity> = withContext(Dispatchers.Default) {
        db.subscriptionQueries.selectAll().executeAsList()
    }

    private fun upsertEntity(entity: SubscriptionEntity, db: SubscriptionsDatabase): Long {
        var subscriptionId = entity.id
        if (subscriptionId == 0L) {
            db.subscriptionQueries.insert(entity)
            subscriptionId = db.subscriptionQueries.lastInsertId().executeAsOne()
        } else {
            db.subscriptionQueries.delete(id = subscriptionId)
            db.subscriptionQueries.insert(entity)
        }
        return subscriptionId
    }
}