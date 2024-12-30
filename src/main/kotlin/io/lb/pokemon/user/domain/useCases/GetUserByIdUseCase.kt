package io.lb.pokemon.user.domain.useCases

import io.ktor.http.HttpStatusCode
import io.lb.pokemon.user.data.model.UserData
import io.lb.pokemon.user.domain.repository.UserRepository
import io.lb.pokemon.util.PokemonException

/**
 * Use case for retrieving a user by their ID.
 *
 * @property repository The repository for interacting with user data.
 */
class GetUserByIdUseCase(
    private val repository: UserRepository
) {
    /**
     * Retrieves a user by their ID.
     *
     * @param userId The ID of the user to retrieve.
     * @return The user data if found.
     * @throws PokemonException if no user is found with the specified ID.
     */
    suspend operator fun invoke(userId: String): UserData {
        return repository.getUserById(userId) ?: throw PokemonException(
            HttpStatusCode.NotFound,
            "There is no user with such ID"
        )
    }
}
