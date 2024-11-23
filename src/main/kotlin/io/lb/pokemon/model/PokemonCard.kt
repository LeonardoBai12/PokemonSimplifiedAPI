package io.lb.pokemon.model

import kotlinx.serialization.Serializable

@Serializable
data class PokemonCard(
    val id: String,
    val pokemonId: Int,
    val imageUrl: String,
    val imageData: ByteArray?,
    val name: String
)
