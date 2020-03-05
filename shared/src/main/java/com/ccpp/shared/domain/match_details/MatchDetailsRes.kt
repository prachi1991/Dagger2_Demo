package com.ccpp.shared.domain.match_details


import com.google.gson.annotations.SerializedName


data class MatchDetailsRes(

    @field:SerializedName("markets")
    val markets: List<MarketsItem?>? = null,

    @field:SerializedName("sessions")
    val sessions: List<SessionsItem?>? = null,

    @field:SerializedName("match")
    val match: Match? = null
)