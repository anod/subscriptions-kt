package auth

import io.ktor.auth.*
import kotlinx.serialization.*

@Serializable
data class LoginCredentials(val username: String, val password: String) : Credential

@Serializable
data class UserIdPrincipal(val username: String, val id: Int): Principal

class PasswordAuthenticator(private val credentials: LoginCredentials) {
    fun authenticate(): UserIdPrincipal {
        if (credentials.username == "alex") {
            return UserIdPrincipal("alex", 1)
        }
        throw IllegalArgumentException("Unknown user ${credentials.username}")
    }
}