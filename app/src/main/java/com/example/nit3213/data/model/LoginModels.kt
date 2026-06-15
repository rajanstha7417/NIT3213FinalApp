package com.example.nit3213.data.model

import com.squareup.moshi.JsonClass

/** Request body sent to the /auth endpoint: { "username": ..., "password": ... } */
@JsonClass(generateAdapter = true)
data class LoginRequest(
    val username: String,
    val password: String
)

/** Successful login response: { "keypass": "topicName" } */
@JsonClass(generateAdapter = true)
data class LoginResponse(
    val keypass: String
)
