package io.lb.pokemon.user.domain.useCases

import io.ktor.http.HttpStatusCode
import io.lb.pokemon.user.data.model.UserUpdateRequest
import io.lb.pokemon.user.domain.repository.UserRepository
import io.lb.pokemon.user.util.isValidEmail
import io.lb.pokemon.user.util.validateEmail
import io.lb.pokemon.user.util.validatePassword
import io.lb.pokemon.util.PokemonException

/**
 * Use case for updating user information.
 *
 * @property repository The repository for interacting with user data.
 */
class UpdateUserUseCase(
    private val repository: UserRepository
) {
    /**
     * Updates the information of a user.
     *
     * @param userId The ID of the user whose information is being updated.
     * @param user The update request containing the new user information.
     */
    suspend operator fun invoke(userId: String, user: UserUpdateRequest) {
        repository.validateEmail(user.email)
        val storedUser = repository.validatePassword(userId, user.password)

        if (user.userName != null && user.userName.isBlank()) {
            throw PokemonException(HttpStatusCode.Conflict, "User must have a name.")
        }

        if (user.email.isValidEmail().not()) {
            throw PokemonException(HttpStatusCode.Conflict, "Invalid email.")
        }

        val updatedUser = storedUser.copy(
            userName = user.userName ?: storedUser.userName,
            email = user.email ?: storedUser.email,
            profilePictureUrl = user.profilePictureUrl ?: storedUser.profilePictureUrl,
        )
        repository.updateUser(updatedUser)
    }
}
