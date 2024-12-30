package io.lb.pokemon.user.data.repository

import io.lb.pokemon.user.data.model.UserData
import io.lb.pokemon.user.data.service.UserDatabaseService
import io.lb.pokemon.user.domain.repository.UserRepository

/**
 * Repository class for interacting with user data.
 */
class UserRepositoryImpl(
    private val service: UserDatabaseService
) : UserRepository {
    override suspend fun createUser(user: UserData): String {
        return service.createUser(user)
    }

    override suspend fun updateUser(user: UserData): Int {
        return service.updateUser(user)
    }

    override suspend fun updatePassword(userId: String, newPassword: String): Int {
        return service.updatePassword(userId, newPassword)
    }

    override suspend fun deleteUser(userId: String): Int {
        return service.deleteUser(userId)
    }

    override suspend fun getUserById(userId: String): UserData? {
        return service.getUserById(userId)
    }

    override suspend fun getUserByEmail(email: String): UserData? {
        return service.getUserByEmail(email)
    }

    override suspend fun isEmailAlreadyInUse(email: String): Boolean {
        return service.isEmailAlreadyInUse(email)
    }

    override suspend fun getUserByPhone(phone: String): UserData? {
        return service.getUserByPhone(phone)
    }

    override suspend fun isPhoneAlreadyInUse(phone: String): Boolean {
        return service.isPhoneAlreadyInUse(phone)
    }
}
