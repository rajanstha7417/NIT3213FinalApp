package com.example.nit3213.data.repository

import com.example.nit3213.data.model.Entity
import com.example.nit3213.data.model.LoginRequest
import com.example.nit3213.data.remote.ApiService
import javax.inject.Inject

/**
 * Real implementation of [NitRepository] backed by Retrofit.
 * Dependencies (the ApiService) are injected via the constructor by Hilt.
 *
 * Network calls are wrapped in runCatching so the ViewModel always gets a
 * tidy Result (success or failure) instead of a thrown exception.
 */
class NitRepositoryImpl @Inject constructor(
    private val api: ApiService
) : NitRepository {

    override suspend fun login(
        location: String,
        username: String,
        password: String
    ): Result<String> = runCatching {
        val response = api.login(location, LoginRequest(username, password))
        response.keypass
    }

    override suspend fun getDashboard(keypass: String): Result<List<Entity>> = runCatching {
        val response = api.getDashboard(keypass)
        response.entities.map { raw ->
            // Convert each property to text and keep the original order.
            val ordered = LinkedHashMap<String, String>()
            raw.forEach { (key, value) -> ordered[key] = value?.toString().orEmpty() }
            Entity(ordered)
        }
    }
}
