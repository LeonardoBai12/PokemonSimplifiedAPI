package io.lb.pokemon.user.domain.model

data class LoginByPhoneData(
    val phone: String,
    val verificationCode: String,
)
