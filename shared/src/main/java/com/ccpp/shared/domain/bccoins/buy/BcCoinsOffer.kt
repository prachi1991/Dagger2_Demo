package com.ccpp.shared.domain.bccoins.buy

data class BcCoinsOffer(
    val bc_coins: Double? = 0.0,
    val cost: Double? = 0.0,
    val id: Int? = 0,
    val title: String? = null
)