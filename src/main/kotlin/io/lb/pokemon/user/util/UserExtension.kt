package io.lb.pokemon.user.util

import io.ktor.http.HttpStatusCode
import io.lb.pokemon.user.data.model.UserData
import io.lb.pokemon.user.domain.repository.UserRepository
import io.lb.pokemon.util.PokemonException
import org.mindrot.jbcrypt.BCrypt

suspend fun UserRepository.validateEmail(email: String?) {
    if (email != null && isEmailAlreadyInUse(email)) {
        throw PokemonException(
            HttpStatusCode.Conflict,
            "Email already in use by another user."
        )
    }
}

suspend fun UserRepository.validatePasswordByEmail(
    email: String,
    password: String,
): UserData {
    val storedUser = getUserByEmail(email) ?: run {
        throw PokemonException(HttpStatusCode.NotFound, "There is no user with such email")
    }

    password.ifEmpty {
        throw PokemonException(HttpStatusCode.Unauthorized, "Invalid password")
    }

    if (!password.passwordCheck(storedUser.password ?: "")) {
        throw PokemonException(HttpStatusCode.Unauthorized, "Invalid password")
    }

    return storedUser
}

suspend fun UserRepository.validatePassword(
    userId: String,
    password: String,
): UserData {
    val storedUser = getUserById(userId) ?: run {
        throw PokemonException(HttpStatusCode.NotFound, "There is no user with such ID")
    }

    password.ifEmpty {
        throw PokemonException(HttpStatusCode.Unauthorized, "Invalid password")
    }

    if (!password.passwordCheck(storedUser.password ?: "")) {
        throw PokemonException(HttpStatusCode.Unauthorized, "Invalid password")
    }

    return storedUser
}


fun String.encrypt(): String? {
    val salt = BCrypt.gensalt(12)
    return BCrypt.hashpw(this, salt)
}

fun String.passwordCheck(encryptedPassword: String): Boolean {
    return BCrypt.checkpw(this, encryptedPassword)
}

fun String?.isValidEmail(): Boolean {
    this ?: return false
    val emailRegex = Regex("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+\$")
    return isNotBlank() && matches(emailRegex)
}

fun String?.isValidPhoneNumber(): Boolean {
    this ?: return false
    val phoneRegex = Regex("^\\+[1-9]\\d{1,14}\$")
    return isNotBlank() && matches(phoneRegex)
}
