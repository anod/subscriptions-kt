package info.anodsplace.subscriptions.database

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import info.anodsplace.subscriptions.database.SubscriptionsDatabase

fun appDatabaseDriverFactory(context: Context): SqlDriver =
    AndroidSqliteDriver(
        schema = SubscriptionsDatabase.Schema,
        context = context,
        name = "TodoDatabase.db"
    )
