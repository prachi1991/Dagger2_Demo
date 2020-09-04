package com.ballchalu.shared.domain.match_details


import com.ballchalu.shared.util.ColorUtils
import com.google.gson.annotations.SerializedName


data class RunnersItem(

    @field:SerializedName("runner")
    var runner: Runner? = null,

    @SerializedName("b")
    var B: String? = "",
    @SerializedName("can_back")
    var canBack: Boolean = false,
    @SerializedName("id")
    var id: Int? = 0,
    @SerializedName("l")
    var L: String? = "",
    @SerializedName("can_lay")
    var canLay: Boolean = false,
    @SerializedName("sid")
    var sid: String? = null,


    var marketId: Int? = null,
    var status: String? = null,
    var runnerPosition: Int? = null,
    var betfairRunnerName: String? = null,
    var betfairSelectionId: String? = null

) {
    fun color(): Int = ColorUtils.getPositionColor(runnerPosition ?: 0)
    fun runner(): String =
        if (runnerPosition == 0 || runnerPosition == null) " " else runnerPosition.toString()

}