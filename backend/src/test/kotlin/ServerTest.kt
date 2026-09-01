import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.lampjuice.database.DatabaseTestHelper
import com.lampjuice.feature.auth.data.repository.UserRepositoryImpl
import com.lampjuice.feature.auth.data.security.JwtConfig
import com.lampjuice.feature.auth.data.security.JwtServiceImpl
import com.lampjuice.feature.auth.data.security.PasswordHasherImpl
import com.lampjuice.feature.auth.presentation.dto.LoginResponse
import com.lampjuice.feature.auth.presentation.dto.RegisterResponse
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.testing.testApplication
import java.util.Date
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

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

    @Test
    fun `auth me returns unauthorized without token`() = testApplication {
        configure()
        val response = client.get("/auth/me")
        assertEquals(HttpStatusCode.Unauthorized, response.status)
    }

    @Test
    fun `auth me returns unauthorized with invalid token`() = testApplication {
        configure()

        val response = client.get("/auth/me") {
            header(
                HttpHeaders.Authorization,
                "Bearer invalid-token"

            )
        }
        assertEquals(HttpStatusCode.Unauthorized, response.status)
    }

    @Test
    fun `login returns user and valid token for valid credentials`() = testApplication {
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

        assertEquals(
            "login@example.com",
            responseBody.email,
        )

        assertTrue(responseBody.token.isNotBlank())

        val meResponse = client.get("/auth/me") {
            header(
                HttpHeaders.Authorization,
                "Bearer ${responseBody.token}",
            )
        }

        assertEquals(HttpStatusCode.OK, meResponse.status)
    }

    @Test
    fun `auth me returns unauthorized with expired token`() = testApplication {
        configure()

        val token = JWT.create()
            .withIssuer("budgetlight")
            .withAudience("budgetlight-client")
            .withClaim("userId", 42L)
            .withClaim("email", "auth-me@example.com")
            .withExpiresAt(
                Date(System.currentTimeMillis() - 1_000L)
            )
            .sign(
                Algorithm.HMAC256("dev-secret-change-me")
            )

        val response = client.get("/auth/me") {
            header(
                HttpHeaders.Authorization,
                "Bearer $token",
            )
        }

        assertEquals(HttpStatusCode.Unauthorized, response.status)
    }

    @Test
    fun `auth me returns unauthorized with wrong audience`() = testApplication {
        configure()

        val jwtService = JwtServiceImpl(
            JwtConfig(
                secret = "dev-secret-change-me",
                issuer = "budgetlight",
                audience = "wrong-audience",
                expirationMs = 3_600_000L,
            )
        )

        val token = jwtService.generateToken(
            userId = 42L,
            email = "auth-me@example.com",
        )

        val response = client.get("/auth/me") {
            header(
                HttpHeaders.Authorization,
                "Bearer $token",
            )
        }

        assertEquals(HttpStatusCode.Unauthorized, response.status)
    }

    @Test
    fun `auth me returns unauthorized with wrong issuer`() = testApplication {
        configure()

        val jwtService = JwtServiceImpl(
            JwtConfig(
                secret = "dev-secret-change-me",
                issuer = "wrong-issuer",
                audience = "budgetlight-client",
                expirationMs = 3_600_000L,
            )
        )

        val token = jwtService.generateToken(
            userId = 42L,
            email = "auth-me@example.com",
        )

        val response = client.get("/auth/me") {
            header(
                HttpHeaders.Authorization,
                "Bearer $token",
            )
        }

        assertEquals(HttpStatusCode.Unauthorized, response.status)
    }

    @Test
    fun `auth me returns unauthorized with token signed by another secret`() = testApplication {
        configure()

        val token = JWT.create()
            .withIssuer("budgetlight")
            .withAudience("budgetlight-client")
            .withClaim("userId", 42L)
            .withClaim("email", "auth-me@example.com")
            .withExpiresAt(
                Date(System.currentTimeMillis() + 3_600_000L)
            )
            .sign(Algorithm.HMAC256("wrong-secret"))

        val response = client.get("/auth/me") {
            header(
                HttpHeaders.Authorization,
                "Bearer $token",
            )
        }

        assertEquals(HttpStatusCode.Unauthorized, response.status)
    }


}
