package io.lb.pokemon.core

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.lb.pokemon.model.PokemonCard
import io.lb.pokemon.util.PokemonException
import java.sql.SQLException
import kotlinx.coroutines.flow.toList

fun Application.routes() {
    val collection = DatabaseClient.client(false)
        .database().getCollection<PokemonCard>("PokemonCard")

    routing {
        authenticate {
            get("/api/pokemon") {
                val amount = call.parameters["amount"]?.toInt() ?: run {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }
                try {
                    val pokemon = collection.find().toList()
                        .shuffled()
                        .take(amount)
                    call.respond(HttpStatusCode.OK, pokemon)
                } catch (e: SQLException) {
                    call.respond(HttpStatusCode.Forbidden, e.message.toString())
                } catch (e: PokemonException) {
                    call.respond(e.code, e.message.toString())
                }
            }
        }
    }
}
