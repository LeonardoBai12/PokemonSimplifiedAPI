package io.lb.pokemon.model

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.sql.Date

data class TokenClaim(
    val name: String,
    val value: String
)

fun generateToken(config: TokenConfig, vararg claims: TokenClaim): String {
    var token = JWT.create()
        .withAudience(config.audience)
        .withIssuer(config.issuer)
        .withExpiresAt(Date(System.currentTimeMillis() + config.expiresIn))
    claims.forEach { claim ->
        token = token.withClaim(claim.name, claim.value)
    }
    return token.sign(Algorithm.HMAC256(config.secret))
}
