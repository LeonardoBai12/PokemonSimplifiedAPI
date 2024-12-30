package io.lb.pokemon.user.data.service

import io.lb.pokemon.user.data.model.UserData

/**
 * Service interface for interacting with the user data table.
 */
interface UserDatabaseService {
    /**
     * Creates a new user in the database.
     *
     * @param user The user data to insert.
     */
    suspend fun createUser(user: UserData): String

    /**
     * Updates an existing user in the database.
     *
     * @param user The updated user data.
     */
    suspend fun updateUser(user: UserData): Int

    /**
     * Updates the password of a user in the database.
     *
     * @param userId The ID of the user whose password to update.
     * @param newPassword The new password.
     */
    suspend fun updatePassword(userId: String, newPassword: String): Int

    /**
     * Deletes a user from the database.
     *
     * @param userId The ID of the user to delete.
     */
    suspend fun deleteUser(userId: String): Int

    /**
     * Retrieves a user by ID from the database.
     *
     * @param userId The ID of the user to retrieve.
     * @return The user data, or null if not found.
     */
    suspend fun getUserById(userId: String): UserData?

    /**
     * Retrieves a user by email from the database.
     *
     * @param email The email of the user to retrieve.
     * @return The user data, or null if not found.
     */
    suspend fun getUserByEmail(email: String): UserData?

    /**
     * Retrieves a user by phone from the database.
     *
     * @param phone The phone of the user to retrieve.
     * @return The user data, or null if not found.
     */
    suspend fun getUserByPhone(phone: String): UserData?

    /**
     * Checks if a phone is already in use in the database.
     *
     * @param phone The phone to check.
     * @return True if the phone is already in use, false otherwise.
     */
    suspend fun isPhoneAlreadyInUse(phone: String): Boolean

    /**
     * Checks if an email is already in use in the database.
     *
     * @param email The email to check.
     * @return True if the email is already in use, false otherwise.
     */
    suspend fun isEmailAlreadyInUse(email: String): Boolean
}
