package com.ballchalu.shared.domain.data

/**
 * Data validation state of the login form.
 */
data class LoginFormState(
    val usernameError: Int? = null,
    val firstName: Int? = null,
    val lastName: Int? = null,
    val passwordError: Int? = null,
    val emailError: Int? = null,
    val confirmPasswordError: Int? = null,
    val isDataValid: Boolean = false
)
