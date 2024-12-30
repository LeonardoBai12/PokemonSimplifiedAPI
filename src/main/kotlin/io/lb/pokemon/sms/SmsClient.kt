package io.lb.pokemon.sms

import aws.sdk.kotlin.services.sns.SnsClient
import aws.sdk.kotlin.services.sns.model.PublishRequest
import kotlin.random.Random

object SmsClient {
    suspend fun sendSms(userPhoneNumber: String, messageToSend: String): Int {
        val expectedCode = (100_000..999_999).random()
        SnsClient {
            region = "sa-east-1"
        }.use { client ->
            val request = PublishRequest {
                phoneNumber = userPhoneNumber
                message = "$messageToSend: $expectedCode"
            }
            client.publish(request)
        }
        return expectedCode
    }
}
