package com.ccpp.shared.domain.match_details


import com.google.gson.annotations.SerializedName


data class RunnersItem(

    @field:SerializedName("runner")
    val runner: Runner? = null,

    @SerializedName("b")
    var B: String? = "",
    @SerializedName("can_back")
    var canBack: Boolean = true,
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("l")
    var L: String? = "",
    @SerializedName("can_lay")
    var canLay: Boolean = true,
    @SerializedName("sid")
    var sid: String? = null
)