package com.ccpp.shared.domain.create_bet

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CreateBetReq(

    @SerializedName("access_token")
    var accessToken: String? = null,

    @SerializedName("match_id")
    var matchId: String? = null,

    @SerializedName("odds_type")
    var oddsType: String? = null,

    @SerializedName("runner_id")
    var runnerId: Int? = 0,

    @SerializedName("odds_val")
    var oddsVal: String? = null,

    @SerializedName("market_id")
    var marketId: Int? = null,

    @SerializedName("heroic_market_type")
    var heroicMarketType: String? = null,

    @SerializedName("stack")
    var stack: String? = null,

    @SerializedName("contestsId")
    var contestsId: Int? = 0,

    var evenTypeTitle: String? = ""
) : Parcelable {


}

