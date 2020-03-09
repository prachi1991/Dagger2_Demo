package com.ccpp.shared.domain.create_bet

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CreateBetReq (
    @SerializedName("match_id")
    @Expose
    var matchId: String? = null,

    @SerializedName("odds_type")
    @Expose
    var oddsType: String? = null,

    @SerializedName("runner_id")
    @Expose
    var runnerId: String? = null,

    @SerializedName("odds_val")
    @Expose
    var oddsVal: String? = null,

    @SerializedName("market_id")
    @Expose
    var marketId: String? = null,

    @SerializedName("heroic_market_type")
    @Expose
    var heroicMarketType: String? = null,

    @SerializedName("stack")
    @Expose
    var stack: String? = null
){


}

