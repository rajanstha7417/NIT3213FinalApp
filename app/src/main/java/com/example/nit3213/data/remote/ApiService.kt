package com.example.nit3213.data.remote

import com.example.nit3213.data.model.DashboardResponse
import com.example.nit3213.data.model.LoginRequest
import com.example.nit3213.data.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Retrofit turns this interface into a working HTTP client.
 * `suspend` lets us call these from a coroutine without blocking the UI thread.
 */
interface ApiService {

    /** POST /{location}/auth  -> e.g. /footscray/auth */
    @POST("{location}/auth")
    suspend fun login(
        @Path("location") location: String,
        @Body body: LoginRequest
    ): LoginResponse

    /** GET /dashboard/{keypass} */
    @GET("dashboard/{keypass}")
    suspend fun getDashboard(
        @Path("keypass") keypass: String
    ): DashboardResponse
}
