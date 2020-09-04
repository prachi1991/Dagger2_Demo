package com.ballchalu.shared.domain

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
    val status: String? = "error",
    val error: Int? = null,
    val totalResults: Int? = 0
)
