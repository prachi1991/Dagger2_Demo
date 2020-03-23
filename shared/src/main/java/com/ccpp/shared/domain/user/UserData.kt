package com.ccpp.shared.domain.user

data class UserData(
    val authentication_token: String? = null,
    val available_coins: Double? = 0.0,
    val bc_coins: Double? = 0.0,
    val coins: Double? = 0.0,
    val email: String? = null,
    val id: Int? = null,
    val user_name: String? = null
)