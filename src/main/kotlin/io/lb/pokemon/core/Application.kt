package io.lb.pokemon.core

import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.lb.pokemon.plugins.configureAuth
import io.lb.pokemon.plugins.configureMonitoring
import io.lb.pokemon.plugins.configureSerialization
import io.lb.pokemon.plugins.configureSession

/**
 * Main function of the server.
 */
fun main() {
    embeddedServer(
        Netty,
        port = 8080,
        host = "0.0.0.0",
        module = Application::module
    ).start(wait = true)
}

/**
 * Application module configuration.
 */
fun Application.module() {
    configureSerialization()
    configureMonitoring()
    configureSession()
    configureAuth()
    routes()
}
