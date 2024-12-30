package io.lb.pokemon.sms

/**
 * Service for interacting with the SMS database.
 */
interface SmsDatabaseService {
    /**
     * Inserts a phone and verification code into the database and schedules automatic deletion after 120 seconds.
     *
     * @param phone The phone number to insert.
     * @param verificationCode The verification code associated with the phone number.
     */
    suspend fun insertValidation(phone: String, verificationCode: Int)

    /**
     * Validates a verification code for the given phone and deletes the record upon successful validation.
     *
     * @param phone The phone number to validate.
     * @param verificationCode The verification code to validate.
     * @return True if validation succeeds, false otherwise.
     */
    suspend fun validateCode(phone: String, verificationCode: String): Boolean
}
