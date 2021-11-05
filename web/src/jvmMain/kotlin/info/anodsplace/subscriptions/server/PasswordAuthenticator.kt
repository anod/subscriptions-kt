package info.anodsplace.subscriptions.server

import io.ktor.auth.*
import kotlinx.serialization.*

@Serializable
data class UserIdPrincipal(val username: String, val id: Int): Principal

class PasswordAuthenticator(private val credentials: LoginRequest) {
    fun authenticate(): UserIdPrincipal {
        if (credentials.username == "alex") {
            return UserIdPrincipal("alex", 1)
        }
        throw IllegalArgumentException("Unknown user ${credentials.username}")
    }
}