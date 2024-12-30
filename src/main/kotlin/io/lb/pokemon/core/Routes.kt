package io.lb.pokemon.core

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.auth.authentication
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.lb.pokemon.model.PokemonCard
import io.lb.pokemon.security.data.model.TokenConfig
import io.lb.pokemon.sms.SmsDatabaseServiceImpl
import io.lb.pokemon.user.data.repository.UserRepositoryImpl
import io.lb.pokemon.user.data.service.UserDatabaseServiceImpl
import io.lb.pokemon.user.domain.useCases.DeleteUserUseCase
import io.lb.pokemon.user.domain.useCases.GetUserByIdUseCase
import io.lb.pokemon.user.domain.useCases.GetUserByPhoneUseCase
import io.lb.pokemon.user.domain.useCases.LoginByPhoneUseCase
import io.lb.pokemon.user.domain.useCases.LoginUseCase
import io.lb.pokemon.user.domain.useCases.SignUpUseCase
import io.lb.pokemon.user.domain.useCases.UpdatePasswordUseCase
import io.lb.pokemon.user.domain.useCases.UpdateUserUseCase
import io.lb.pokemon.user.domain.useCases.UserUseCases
import io.lb.pokemon.user.routes.userRoutes
import io.lb.pokemon.util.PokemonException
import java.sql.SQLException
import kotlinx.coroutines.flow.toList

val embedded = true

fun Application.routes() {
    val collection = DatabaseClient.client(embedded)
        .database().getCollection<PokemonCard>("PokemonCard")
    val repository = UserRepositoryImpl(
        service = UserDatabaseServiceImpl()
    )
    val tokenConfig = TokenConfig.tokenConfig(config = environment.config, embedded = true)

    userRoutes(
        smsDatabaseService = SmsDatabaseServiceImpl(),
        useCases = UserUseCases(
            deleteUserUseCase = DeleteUserUseCase(repository),
            getUserByIdUseCase = GetUserByIdUseCase(repository),
            getUserByPhoneUseCase = GetUserByPhoneUseCase(repository),
            loginUseCase = LoginUseCase(repository, tokenConfig),
            loginByPhoneUseCase = LoginByPhoneUseCase(repository, tokenConfig),
            signUpUseCase = SignUpUseCase(repository),
            updatePasswordUseCase = UpdatePasswordUseCase(repository),
            updateUserUseCase = UpdateUserUseCase(repository),
        )
    )

    routing {
        authentication {
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
