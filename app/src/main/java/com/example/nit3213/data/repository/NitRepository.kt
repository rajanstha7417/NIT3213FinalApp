package com.example.nit3213.data.repository

import com.example.nit3213.data.model.Entity

/**
 * Repository Pattern: a single middle layer between the ViewModels and the
 * network. ViewModels depend on this interface (not on Retrofit directly),
 * which keeps them clean and makes them easy to unit-test with a fake/mock.
 */
interface NitRepository {

    /** Logs in and returns the keypass on success. */
    suspend fun login(location: String, username: String, password: String): Result<String>

    /** Fetches the list of entities for the given keypass. */
    suspend fun getDashboard(keypass: String): Result<List<Entity>>
}
