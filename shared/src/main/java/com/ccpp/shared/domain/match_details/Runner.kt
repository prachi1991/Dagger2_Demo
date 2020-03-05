package com.ccpp.shared.domain.match_details


import com.google.gson.annotations.SerializedName


data class Runner(

    @field:SerializedName("lay")
    val lay: Any? = null,

    @field:SerializedName("can_back")
    val canBack: Boolean? = null,

    @field:SerializedName("back")
    val back: Double? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("betfair_selection_id")
    val betfairSelectionId: String? = null,

    @field:SerializedName("betfair_runner_name")
    val betfairRunnerName: String? = null,

    @field:SerializedName("can_lay")
    val canLay: Boolean? = null,

    @field:SerializedName("status")
    val status: String? = null
)