package io.lb.pokemon.model

import java.io.FileInputStream
import java.util.Properties

data class TokenConfig(
    val issuer: String,
    val audience: String,
    val expiresIn: Long,
    val secret: String
) {
    companion object {
        fun buildTokenConfig(
            embedded: Boolean = false
        ): TokenConfig {
            val secret = if (embedded) {
                val properties = Properties()
                val fileInputStream = FileInputStream("local.properties")
                properties.load(fileInputStream)

                properties.getProperty("jwt.secret_key")
            } else {
                System.getenv("SECRET_KEY")
            }

            return TokenConfig(
                issuer = "http://0.0.0.0:8080",
                audience = "users",
                expiresIn = 365L * 1000L * 60L * 60L * 24L,
                secret = secret
            )
        }
    }
}
