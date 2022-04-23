package info.anodsplace.subscriptions.server

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.JWTVerifier
import io.ktor.server.application.*

class JwtToken(
    private val secret: String,
    private val issuer: String,
    private val audience: String,
    val realm: String,
) {
    fun forUser(user: UserIdPrincipal): String {
        val hasuraClaims = mapOf(
            "x-hasura-allowed-roles" to listOf("user"),
            "x-hasura-default-role" to "user",
            "x-hasura-user-id" to user.id.toString()
        )
        return JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("https://hasura.io/jwt/claims", hasuraClaims)
            .sign(Algorithm.HMAC256(secret))
    }

    val verifier: JWTVerifier
        get() = JWT
            .require(Algorithm.HMAC256(secret))
            .withAudience(audience)
            .withIssuer(issuer)
            .build()

    constructor(environment: ApplicationEnvironment) : this(
        secret = environment.config.property("jwt.secret").getString(),
        issuer = environment.config.property("jwt.issuer").getString(),
        audience = environment.config.property("jwt.audience").getString(),
        realm = environment.config.property("jwt.realm").getString(),
    )
}