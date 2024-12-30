package io.lb.pokemon.user.data.service

import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import io.lb.pokemon.core.DatabaseClient
import io.lb.pokemon.core.database
import io.lb.pokemon.core.embedded
import io.lb.pokemon.user.data.model.UserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.flow.singleOrNull

/**
 * Service class for interacting with the user data table in the PostgreSQL database.
 */
class UserDatabaseServiceImpl : UserDatabaseService {
    private val collection = DatabaseClient.client(embedded)
        .database().getCollection<UserData>("User")

    override suspend fun createUser(user: UserData): String = withContext(Dispatchers.IO) {
        collection.insertOne(user)
        return@withContext user.userId
    }

    override suspend fun updateUser(user: UserData): Int = withContext(Dispatchers.IO) {
        val queryParams = Filters.eq(UserData::userId.name, user.userId)
        val updateParams = Updates.combine(
            Updates.set(UserData::userName.name, user.userName),
            Updates.set(UserData::email.name, user.email),
            Updates.set(UserData::profilePictureUrl.name, user.profilePictureUrl)
        )
        val result = collection.updateOne(queryParams, updateParams)
        result.modifiedCount.toInt()
    }

    override suspend fun updatePassword(userId: String, newPassword: String): Int = withContext(Dispatchers.IO) {
        val queryParams = Filters.eq(UserData::userId.name, userId)
        val updateParams = Updates.set(UserData::password.name, newPassword)
        val result = collection.updateOne(queryParams, updateParams)
        result.modifiedCount.toInt()
    }

    override suspend fun deleteUser(userId: String): Int = withContext(Dispatchers.IO) {
        val queryParams = Filters.eq(UserData::userId.name, userId)
        val result = collection.deleteOne(queryParams)
        result.deletedCount.toInt()
    }

    override suspend fun getUserById(userId: String): UserData? = withContext(Dispatchers.IO) {
        val queryParams = Filters.eq(UserData::userId.name, userId)
        collection.find(queryParams).limit(1).singleOrNull()
    }

    override suspend fun getUserByEmail(email: String): UserData? = withContext(Dispatchers.IO) {
        val queryParams = Filters.eq(UserData::email.name, email)
        collection.find(queryParams).limit(1).singleOrNull()
    }

    override suspend fun getUserByPhone(phone: String): UserData? = withContext(Dispatchers.IO) {
        val queryParams = Filters.eq(UserData::phone.name, phone)
        collection.find(queryParams).limit(1).singleOrNull()
    }

    override suspend fun isPhoneAlreadyInUse(phone: String): Boolean = withContext(Dispatchers.IO) {
        val queryParams = Filters.eq(UserData::phone.name, phone)
        collection.find(queryParams).limit(1).singleOrNull() != null
    }

    override suspend fun isEmailAlreadyInUse(email: String): Boolean = withContext(Dispatchers.IO) {
        val queryParams = Filters.eq(UserData::email.name, email)
        collection.find(queryParams).limit(1).singleOrNull() != null
    }
}
