package io.lb.pokemon.sms

import kotlinx.serialization.Serializable

/**
 * Data class for SMS validation data.
 *
 * @property phone The phone number to validate.
 * @property verificationCode The verification code to validate.
 */
@Serializable
data class ValidationData(
    val phone: String,
    val verificationCode: Int
)
