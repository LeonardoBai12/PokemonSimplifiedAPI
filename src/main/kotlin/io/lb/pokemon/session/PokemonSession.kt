package io.lb.pokemon.session

import kotlinx.serialization.Serializable

@Serializable
data class PokemonSession(
    val clientId: String,
    val sessionId: String,
)
