package io.lb.pokemon.user.domain.useCases

import io.ktor.http.HttpStatusCode
import io.lb.pokemon.model.TokenConfig
import io.lb.pokemon.security.data.model.TokenClaim
import io.lb.pokemon.security.generateToken
import io.lb.pokemon.user.data.model.LoginResponse
import io.lb.pokemon.user.domain.repository.UserRepository
import io.lb.pokemon.user.util.validatePasswordByEmail
import io.lb.pokemon.util.PokemonException

/**
 * Use case for user login.
 *
 * @property repository The repository for interacting with user data.
 * @property tokenConfig The configuration for generating authentication tokens.
 */
class LoginByPhoneUseCase(
    private val repository: UserRepository,
    private val tokenConfig: TokenConfig
) {
    /**
     * Authenticates a user and generates an authentication token.
     *
     * @param phone The email of the user attempting to log in.
     * @return An authentication token.
     * @throws PokemonException if the provided email is invalid.
     * @throws PokemonException if the provided password is invalid.
     */
    suspend operator fun invoke(phone: String): LoginResponse {
        val user = repository.getUserByPhone(phone) ?: run {
            throw PokemonException(
                HttpStatusCode.NotFound,
                "There is no user with such phone number"
            )
        }

        return LoginResponse(
            userId = user.userId,
            token = generateToken(
                config = tokenConfig,
                TokenClaim(
                    name = "userId",
                    value = user.userId
                )
            )
        )
    }
}