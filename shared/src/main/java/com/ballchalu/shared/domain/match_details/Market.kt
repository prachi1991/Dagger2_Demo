package com.ballchalu.shared.domain.match_details


import com.google.gson.annotations.SerializedName


data class Market(

    @field:SerializedName("betfair_market_type")
    val betfairMarketType: String? = null,

    @field:SerializedName("heroic_market_type")
    val heroicMarketType: String? = null,

    @field:SerializedName("extra_info")
    val extraInfo: Any? = null,

    @field:SerializedName("betfair_market_id")
    val betfairMarketId: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("runners")
    val runners: List<RunnersItem>? = null,

    @field:SerializedName("status")
    val status: String? = null,

    @SerializedName("tournament_id")
    var tournamentId: Int? = null
)