package com.ccpp.shared.domain.my_bets

import com.ccpp.shared.domain.match_details.Session
import com.google.gson.annotations.SerializedName


data class Info(

    @field:SerializedName("market")
    val market: Market? = null,

    @field:SerializedName("session")
    val session: Session? = null,

    @field:SerializedName("start_time")
    val startTime: String? = null,

    @field:SerializedName("team1")
    val team1: String? = null,

    @field:SerializedName("team2")
    val team2: String? = null,

    @field:SerializedName("title")
    val title: String? = null
)