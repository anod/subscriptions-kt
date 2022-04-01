package info.anodsplace.subscriptions.database

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver

fun appDatabaseDriverFactory(): SqlDriver {
    val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
    SubscriptionsDatabase.Schema.create(driver)
    return driver
}
