package com.lampjuice.database

import com.lampjuice.feature.auth.data.database.UserTable
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.config.ApplicationConfig
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

object DatabaseFactory {
    fun init(config: ApplicationConfig) {
        val dataSource = createDataSource(config)

        Database.connect(dataSource)

        transaction {
            SchemaUtils.create(UserTable)
        }
    }

    private fun createDataSource(
        config: ApplicationConfig
    ): HikariDataSource {
        val hikariConfig = HikariDataSource().apply {
            jdbcUrl = config.property("database.url").getString()
            username = config.property("database.username").getString()
            password = config.property("database.password").getString()

            driverClassName = "org.postgresql.Driver"

            maximumPoolSize = 10
            minimumIdle = 2

            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"

            validate()
        }

        return HikariDataSource(hikariConfig)
    }

}

