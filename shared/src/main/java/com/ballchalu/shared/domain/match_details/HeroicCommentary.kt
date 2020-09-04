package com.ballchalu.shared.domain.match_details


import com.google.gson.annotations.SerializedName


data class HeroicCommentary(

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("event")
    val event: String? = null
)