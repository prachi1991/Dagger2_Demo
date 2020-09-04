package com.ballchalu.shared.domain.my_bets

import com.ballchalu.shared.domain.match_details.RunnersItem
import com.google.gson.annotations.SerializedName


data class Market(

    @field:SerializedName("betfair_market_type")
    val betfairMarketType: String? = null,

    @field:SerializedName("heroic_market_type")
    val heroicMarketType: String? = null,

    @field:SerializedName("betfair_market_id")
    val betfairMarketId: String? = null,

    @field:SerializedName("match_id")
    val matchId: Int? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("runners")
    val runners: List<RunnersItem?>? = null,

    @field:SerializedName("status")
    val status: String? = null
)