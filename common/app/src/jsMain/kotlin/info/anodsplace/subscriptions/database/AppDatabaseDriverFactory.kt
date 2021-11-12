package info.anodsplace.subscriptions.database

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.sqljs.initSqlDriver
import info.anodsplace.subscriptions.asFlow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.js.Promise

fun appDatabaseDriverFactory(): Flow<SqlDriver> =
    initSqlDriver(SubscriptionsDatabase.Schema).asFlow()
