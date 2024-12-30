package io.lb.pokemon.user.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginByPhoneData(
    val phone: String,
    val verificationCode: Int,
)
