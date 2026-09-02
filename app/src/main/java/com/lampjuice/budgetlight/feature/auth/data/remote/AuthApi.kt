package com.lampjuice.budgetlight.feature.auth.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class AuthApi(
    private val client: HttpClient,
    private val baseUrl: String,
) {
    suspend fun register(
        request: RegisterRequestDto,
    ): RegisterResponseDto = client
        .post("${baseUrl}auth/register") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        .body()

    suspend fun login(
        request: LoginRequestDto,
    ): LoginResponseDto = client
        .post("${baseUrl}auth/login") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        .body()
}
