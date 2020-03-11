package com.ccpp.shared.domain.create_bet

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class CreateSessionBetReq (
    @SerializedName("access_token")
    @Expose
    var accessToken: String? = null,
    @SerializedName("match_id")
    @Expose
    var matchId: String? = null,
    @SerializedName("runs")
    @Expose
    var runs: String? = null,
    @SerializedName("session_bet_type")
    @Expose
    var sessionBetType: String? = null,
    @SerializedName("session_id")
    @Expose
    var sessionId: String? = null,
    @SerializedName("session_run_id")
    @Expose
    var sessionRunId: String? = null,
    @SerializedName("odd_value")
    @Expose
    var oddValue: String? = null,
    @SerializedName("coins_debited")
    @Expose
    var coinsDebited: String? = null,
    @SerializedName("contestsId")
    @Expose
    var contestsId: String? = null

)