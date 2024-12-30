package io.lb.pokemon.user.data.model

import kotlinx.serialization.Serializable

/**
 * Data class representing a request to login.
 *
 * @property password The user's password.
 * @property email The user's email address.
 */
@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)
