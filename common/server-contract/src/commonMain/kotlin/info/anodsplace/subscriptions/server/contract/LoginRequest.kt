package info.anodsplace.subscriptions.server.contract

import kotlinx.serialization.*

@Serializable
data class LoginRequest(val username: String, val password: String)

@Serializable
data class LoginResponse(val token: String)
