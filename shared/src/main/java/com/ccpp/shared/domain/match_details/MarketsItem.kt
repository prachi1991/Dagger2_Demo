package com.ccpp.shared.domain.match_details


import com.google.gson.annotations.SerializedName


data class MarketsItem(

    @field:SerializedName("market")
    val market: Market? = null
)