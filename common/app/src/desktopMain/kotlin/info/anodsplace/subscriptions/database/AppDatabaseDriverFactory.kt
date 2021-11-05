package info.anodsplace.subscriptions.database

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import info.anodsplace.subscriptions.database.SubscriptionsDatabase
import java.io.File

fun appDatabaseDriverFactory(): SqlDriver {
    val databasePath = File(System.getProperty("java.io.tmpdir"), "SubscriptionsDatabase.db")
    val driver = JdbcSqliteDriver(url = "jdbc:sqlite:${databasePath.absolutePath}")
    SubscriptionsDatabase.Schema.create(driver)

    return driver
}
