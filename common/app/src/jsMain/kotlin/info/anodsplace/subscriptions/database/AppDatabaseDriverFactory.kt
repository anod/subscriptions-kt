package info.anodsplace.subscriptions.database

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.sqljs.initSqlDriver
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.js.Promise

fun appDatabaseDriverFactory(): Flow<SqlDriver> =
    initSqlDriver(SubscriptionsDatabase.Schema).asFlow()


fun <T : Any> Promise<T>.asFlow(): Flow<T> = flow {
    val channel = Channel<T>(Channel.CONFLATED)

    then(
        onFulfilled = { value ->
            channel.trySend(value)
            channel.close()
        },
        onRejected = {
            channel.close(it)
        }
    )

    for (item in channel) {
        emit(item)
    }
}