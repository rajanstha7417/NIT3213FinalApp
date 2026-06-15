package com.example.nit3213.data.model

import com.squareup.moshi.JsonClass

/**
 * Dashboard response: { "entities": [ {...}, {...} ], "entityTotal": 7 }.
 *
 * The fields inside each entity change depending on your topic (keypass),
 * so each entity is parsed as a generic map of property-name -> value.
 * This keeps the app working no matter which topic the API returns.
 */
@JsonClass(generateAdapter = true)
data class DashboardResponse(
    val entities: List<Map<String, Any?>>,
    val entityTotal: Int
)
