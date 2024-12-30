package io.lb.pokemon.user.domain.useCases

import io.ktor.http.HttpStatusCode
import io.lb.pokemon.user.data.model.UserCreateRequest
import io.lb.pokemon.user.data.model.UserData
import io.lb.pokemon.user.domain.repository.UserRepository
import io.lb.pokemon.user.util.encrypt
import io.lb.pokemon.user.util.isValidEmail
import io.lb.pokemon.user.util.isValidPhoneNumber
import io.lb.pokemon.user.util.validateEmail
import io.lb.pokemon.util.PokemonException

/**
 * Use case for user sign up.
 *
 * @property repository The repository for interacting with user data.
 */
class SignUpUseCase(
    private val repository: UserRepository
) {
    /**
     * Registers a new user.
     *
     * @param user The user creation request containing user details.
     * @return The ID of the newly created user.
     * @throws PokemonException if there are validation errors or if user creation fails.
     */
    suspend operator fun invoke(user: UserCreateRequest): String {
        repository.validateEmail(user.email)

        if (user.userName.isBlank()) {
            throw PokemonException(HttpStatusCode.Conflict, "User must have a name.")
        }

        if (user.email.isValidEmail().not()) {
            throw PokemonException(HttpStatusCode.Conflict, "Invalid email.")
        }

        if (user.phone.isValidPhoneNumber().not()) {
            throw PokemonException(HttpStatusCode.Conflict, "Invalid phone number.")
        }

        if (user.password.length < 8) {
            throw PokemonException(
                HttpStatusCode.Conflict,
                "Password must have more than 8 characters."
            )
        }

        val hashedPassword = user.password.encrypt()
        val userData = UserData(
            userName = user.userName,
            phone = user.phone,
            password = hashedPassword,
            email = user.email,
            profilePictureUrl = user.profilePictureUrl,
        )
        repository.createUser(userData)
        return userData.userId
    }
}
