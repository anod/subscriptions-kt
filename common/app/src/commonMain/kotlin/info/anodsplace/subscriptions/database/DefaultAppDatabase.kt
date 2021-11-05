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

class DefaultAppDatabase(driver: Flow<SqlDriver>, scope: CoroutineScope) : AppDatabase {

    constructor(driver: SqlDriver, scope: CoroutineScope) : this(flowOf(driver), scope)

    private val db: Flow<SubscriptionsDatabase> = driver.map {
        SubscriptionsDatabase(
            driver = it,
            PeriodAdapter = Period.Adapter(
                periodAdapter = EnumColumnAdapter()
            )
        )
    }
        .stateIn(scope, started = SharingStarted.Lazily, initialValue = null)
        .filterNotNull()

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun observeAll(): Flow<List<SubscriptionEntity>> =
        db.flatMapLatest { it.subscriptionQueries.selectAll().asFlow() }.mapToList()

    override suspend fun select(id: Long): SubscriptionEntity? = withContext(Dispatchers.Default) {
        return@withContext db.single().subscriptionQueries.select(id = id).executeAsOneOrNull()
    }

    override suspend fun upsert(entity: SubscriptionEntity): Long = withContext(Dispatchers.Default) {
        val db = db.single()
        return@withContext db.transactionWithResult {
            var subscriptionId = entity.id
            if (subscriptionId == 0L) {
                db.subscriptionQueries.insert(entity)
                subscriptionId = db.subscriptionQueries.lastInsertId().executeAsOne()
            } else {
                db.subscriptionQueries.delete(id = subscriptionId)
                db.subscriptionQueries.insert(entity)
            }
            return@transactionWithResult subscriptionId
        }
    }

    override suspend fun delete(id: Long): Unit = withContext(Dispatchers.Default) {
        db.single().subscriptionQueries.delete(id = id)
    }

    override suspend fun clear(): Unit = withContext(Dispatchers.Default) {
        db.single().subscriptionQueries.clear()
    }

    override suspend fun loadSubscriptions(): List<SubscriptionEntity> = withContext(Dispatchers.Default) {
        db.single().subscriptionQueries.selectAll().executeAsList()
    }
}