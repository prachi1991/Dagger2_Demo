package com.ballchalu.shared.domain.match_details


import com.ballchalu.shared.util.ColorUtils
import com.google.gson.annotations.SerializedName


data class Runner(

    @field:SerializedName("lay")
    var lay: String? = null,

    @field:SerializedName("can_back")
    var canBack: Boolean = false,

    @field:SerializedName("back")
    var back: String? = null,

    @field:SerializedName("id")
    var id: Int? = null,

    @field:SerializedName("betfair_selection_id")
    var betfairSelectionId: String? = null,

    @field:SerializedName("betfair_runner_name")
    var betfairRunnerName: String? = null,

    @field:SerializedName("can_lay")
    var canLay: Boolean = false,

    @field:SerializedName("status")
    var status: String? = null,

    var marketId: Int? = 0,
    var runnerPosition: Int? = 0

) {
    fun color(): Int = ColorUtils.getPositionColor(runnerPosition ?: 0)
    fun runner(): String = if (runnerPosition == 0) "" else runnerPosition.toString()
}