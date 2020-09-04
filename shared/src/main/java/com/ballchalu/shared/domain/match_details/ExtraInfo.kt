package com.ballchalu.shared.domain.match_details


import com.google.gson.annotations.SerializedName


data class ExtraInfo(

    @field:SerializedName("over")
    val over: String? = null,

    @field:SerializedName("team_name")
    val teamName: String? = null
)