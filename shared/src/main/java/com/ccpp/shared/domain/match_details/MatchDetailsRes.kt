package com.ccpp.shared.domain.match_details


import com.google.gson.annotations.SerializedName


data class MatchDetailsRes(
//
    @field:SerializedName("markets")
    var markets: List<MarketsItem>? = null,

    @field:SerializedName("sessions")
    var sessions: List<SessionsItem>? = null,

    @field:SerializedName("match")
    var match: Match? = null
)