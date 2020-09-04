package com.ballchalu.shared.domain.bccoins.buy

import com.ballchalu.shared.domain.user.UserData

data class BcCoinsTransaction(
    val bc_coins_offer: BcCoinsOffer? = null,
    val id: Int? = null,
    val user: UserData? = null
)