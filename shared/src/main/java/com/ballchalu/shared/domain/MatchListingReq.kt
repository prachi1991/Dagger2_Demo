package com.ballchalu.shared.domain

import com.google.gson.annotations.SerializedName

data class MatchListingReq(

    @SerializedName("event_type")
    val event_type: String? = null,

    @SerializedName("play_status")
    val play_status: String? = null


)