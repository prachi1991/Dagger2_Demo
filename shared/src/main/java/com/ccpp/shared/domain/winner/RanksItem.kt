package com.ccpp.shared.domain.winner

import com.google.gson.annotations.SerializedName

data class RanksItem(

    @field:SerializedName("user_id")
    val userId: Int? = null,

    @field:SerializedName("user_name")
    val userName: String? = null,

    @field:SerializedName("rank")
    val rank: Int? = null,

    @field:SerializedName("won_coins")
    val wonCoins: Double? = null
)