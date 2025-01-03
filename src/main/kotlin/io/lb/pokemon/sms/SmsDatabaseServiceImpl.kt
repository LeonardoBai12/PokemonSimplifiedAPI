package io.lb.pokemon.sms

import com.mongodb.client.model.Filters
import io.lb.pokemon.core.DatabaseClient
import io.lb.pokemon.core.database
import io.lb.pokemon.core.embedded
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.singleOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Service for interacting with the SMS validation database.
 */
class SmsDatabaseServiceImpl : SmsDatabaseService {
    private val collection = DatabaseClient.client(embedded)
        .database().getCollection<ValidationData>("SmsValidation")

    override suspend fun insertValidation(phone: String, verificationCode: Int) = withContext(Dispatchers.IO) {
        val validationData = ValidationData(phone = phone, verificationCode = verificationCode)
        collection.insertOne(validationData)

        CoroutineScope(Dispatchers.IO).launch {
            delay(120_000)
            collection.deleteOne(Filters.eq(ValidationData::phone.name, phone))
        }
        Unit
    }

    override suspend fun validateCode(phone: String, verificationCode: Int): Boolean = withContext(Dispatchers.IO) {
        val queryParams = Filters.and(
            Filters.eq(ValidationData::phone.name, phone),
            Filters.eq(ValidationData::verificationCode.name, verificationCode)
        )
        val validation = collection.find(queryParams).limit(1).singleOrNull()

        validation?.let {
            collection.deleteOne(Filters.eq(ValidationData::phone.name, phone))
            true
        } ?: false
    }
}
