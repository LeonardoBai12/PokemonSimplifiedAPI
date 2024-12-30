package io.lb.pokemon.user.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class RequestSmsData(
    val phone: String,
)
