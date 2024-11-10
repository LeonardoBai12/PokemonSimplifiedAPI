package io.lb.pokemon.util

import io.ktor.http.HttpStatusCode

data class PokemonException(
    val code: HttpStatusCode,
    override val message: String?
) : Exception()
