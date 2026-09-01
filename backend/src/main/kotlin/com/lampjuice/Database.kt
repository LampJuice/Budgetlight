package com.lampjuice

import com.lampjuice.database.DatabaseFactory
import io.ktor.server.application.Application

fun Application.configureDatabase() {
    DatabaseFactory.init(environment.config)
}
