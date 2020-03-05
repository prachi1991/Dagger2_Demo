package com.ccpp.shared.domain.match_details


import com.google.gson.annotations.SerializedName


data class RunnersItem(

    @field:SerializedName("runner")
    val runner: Runner? = null
)