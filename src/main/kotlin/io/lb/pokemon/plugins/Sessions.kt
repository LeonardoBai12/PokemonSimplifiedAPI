package io.lb.pokemon.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.sessions.Sessions
import io.ktor.server.sessions.cookie
import io.lb.pokemon.session.PokemonSession

private const val SESSION_NAME = "PokemonSessions"

fun Application.configureSession() {
    install(Sessions) {
        cookie<PokemonSession>(SESSION_NAME)
    }
}
