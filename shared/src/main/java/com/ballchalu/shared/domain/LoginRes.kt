package com.ballchalu.shared.domain

data class LoginRes(
    val access_token: String? = null,
    val refresh_token: String? = null,
    val created_at: Int? = null,
    val token_taype: String? = null,
    val message: String? = null,
    val success: Boolean? = null
)
