package com.ccpp.shared.domain

data class LoginRes(
    val access_token: String? = null,
    val refresh_token: String? = null,
    val created_at: Int? = null,
    val token_taype: String? = null,
    val expires_in: Int? = null
)
