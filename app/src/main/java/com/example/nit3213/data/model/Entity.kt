package com.example.nit3213.data.model

import java.io.Serializable

/**
 * A single item shown in the Dashboard list and on the Details screen.
 *
 * [properties] keeps the original order of the fields returned by the API.
 * Serializable so we can pass a whole Entity to the Details screen via Intent.
 */
data class Entity(
    val properties: LinkedHashMap<String, String>
) : Serializable {

    /** First property is used as the headline in the list and details. */
    val title: String
        get() = properties.values.firstOrNull().orEmpty().ifBlank { "Entity" }

    /** Everything except the long description (shown as the list summary). */
    val summary: List<Pair<String, String>>
        get() = properties.entries
            .filter { it.key.lowercase() != "description" }
            .map { it.key to it.value }

    /** The long description, only shown on the Details screen. */
    val description: String?
        get() = properties.entries
            .firstOrNull { it.key.lowercase() == "description" }
            ?.value
}
