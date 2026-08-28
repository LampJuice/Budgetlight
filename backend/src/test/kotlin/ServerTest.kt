package com.lampjuice

import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.testApplication
import kotlin.test.*

class ServerTest {

    @Test
    fun `health endpoint returns 200`() = testApplication {
        configure()
        assertEquals(
            HttpStatusCode.OK,
            client.get("/health").status
        )
    }

}
