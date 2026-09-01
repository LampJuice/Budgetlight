import com.lampjuice.database.DatabaseTestHelper
import com.lampjuice.feature.auth.data.repository.UserRepositoryImpl
import com.lampjuice.feature.auth.data.security.PasswordHasherImpl
import com.lampjuice.feature.auth.presentation.dto.LoginResponse
import com.lampjuice.feature.auth.presentation.dto.RegisterResponse
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.testing.testApplication
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull

class ServerTest {

    @BeforeTest
    fun setUp() {
        DatabaseTestHelper.connect()
        DatabaseTestHelper.createSchema()
        DatabaseTestHelper.clearUsers()
    }

    @AfterTest
    fun tearDown() {
        DatabaseTestHelper.clearUsers()
    }

    @Test
    fun `health endpoint returns 200`() = testApplication {
        configure()
        assertEquals(
            HttpStatusCode.OK,
            client.get("/health").status
        )
    }

    @Test
    fun `register creates new user`() = testApplication {
        configure()
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val response = client.post("/auth/register") {
            contentType(ContentType.Application.Json)
            setBody(
                """
                    {
                	    "email": "integration@example.com",
                	    "password": "SuperSecretPassword"
                    }
                """.trimIndent()
            )
        }

        assertEquals(HttpStatusCode.Created, response.status)

        val responseBody = response.body<RegisterResponse>()
        assertEquals("integration@example.com", responseBody.email)
    }

    @Test
    fun `register returns conflict when email already exists`() = testApplication {
        configure()

        val request = """
            {
                "email": "duplicate-integration@example.com",
                "password": "SuperSecretPassword"
            }
        """.trimIndent()

        val firstResponse = client.post("/auth/register") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }

        assertEquals(HttpStatusCode.Created, firstResponse.status)

        val secondResponse = client.post("/auth/register") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }

        assertEquals(HttpStatusCode.Conflict, secondResponse.status)
    }

    @Test
    fun `register stores password hash`() = testApplication {
        configure()

        val email = "password-hash-integration@example.com"
        val password = "SuperSecretPassword"

        val response = client.post("/auth/register") {
            contentType(ContentType.Application.Json)
            setBody(
                """
                    {
                	    "email": "$email",
                	    "password": "$password"
                    }
                """.trimIndent()
            )
        }

        assertEquals(HttpStatusCode.Created, response.status)

        val user = UserRepositoryImpl().getUserByEmail(email)

        assertNotNull(user)

        assertNotEquals(password, user.passwordHash)

        val passwordHasher = PasswordHasherImpl()
        assertEquals(
            true,
            passwordHasher.verify(password, user.passwordHash)
        )
        assertFalse(
            passwordHasher.verify("WrongPassword", user.passwordHash)
        )
    }

    @Test
    fun `login returns user for valid credentials`() = testApplication {
        configure()

        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }

        val request = """
            {
                "email": "login@example.com",
                "password": "SuperSecretPassword"
            }
        """.trimIndent()

        val registerResponse = client.post("/auth/register") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }

        assertEquals(HttpStatusCode.Created, registerResponse.status)

        val loginResponse = client.post("/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }

        assertEquals(HttpStatusCode.OK, loginResponse.status)

        val responseBody = loginResponse.body<LoginResponse>()
        assertEquals("login@example.com", responseBody.email)
    }

    @Test
    fun `login returns unauthorized for wrong password`() = testApplication {
        configure()

        val registerRequest = """
            {
                "email": "wrong-password-integration@example.com",
                "password": "SuperSecretPassword"
            }
        """.trimIndent()

        val registerResponse = client.post("/auth/register") {
            contentType(ContentType.Application.Json)
            setBody(registerRequest)
        }

        assertEquals(HttpStatusCode.Created, registerResponse.status)

        val loginResponse = client.post("/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(
                """
                    {
                	    "email": "wrong-password-integration@example.com",
                	    "password": "WrongPassword"
                    }
                """.trimIndent()
            )
        }

        assertEquals(HttpStatusCode.Unauthorized, loginResponse.status)
    }

    @Test
    fun `login returns unauthorized for unknown email`() = testApplication {
        configure()
        val loginResponse = client.post("/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(
                """
                    {
                	    "email": "unknown-integration@example.com",
                	    "password": "SuperSecretPassword"
                    }
                """.trimIndent()
            )

        }
        assertEquals(HttpStatusCode.Unauthorized, loginResponse.status)
    }
}
