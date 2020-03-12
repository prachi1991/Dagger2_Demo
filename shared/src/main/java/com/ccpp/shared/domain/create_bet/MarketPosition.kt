package com.ccpp.shared.domain.create_bet

import com.ccpp.shared.domain.match_details.Runner
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class MarketPosition(
    @SerializedName("market_id")
    @Expose
    var marketId: Int? = null,

    @SerializedName("heroic_market_type")
    @Expose
    var heroicMarketType: String? = null,

    @SerializedName("runners")
    @Expose
    var runners: List<Runner>? = null
)