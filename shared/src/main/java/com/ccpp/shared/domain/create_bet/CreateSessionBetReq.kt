package com.ccpp.shared.domain.create_bet

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CreateSessionBetReq (
    @SerializedName("access_token")
    var accessToken: String? = null,

    @SerializedName("match_id")
    var matchId: String? = null,

    @SerializedName("runs")
    var runs: String? = null,

    @SerializedName("session_bet_type")
    var sessionBetType: String? = null,

    @SerializedName("session_id")
    var sessionId: Int? = null,

    @SerializedName("session_run_id")
    var sessionRunId: Int? = null,

    @SerializedName("heroic_market_type")
    var heroicMarketType: String? = null,

    @SerializedName("odd_value")
    var oddValue: String? = null,

    @SerializedName("coins_debited")
    var coinsDebited: String? = null,

    @SerializedName("contestsId")
    var contestsId: Int? = null,

    var evenTypeTitle: String? = ""
) : Parcelable