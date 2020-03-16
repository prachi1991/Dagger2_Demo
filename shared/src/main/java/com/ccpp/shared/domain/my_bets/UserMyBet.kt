package com.ccpp.shared.domain.my_bets

import com.ccpp.shared.util.ConstantsBase
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import java.util.*


data class UserMyBet(
    @SerializedName("id")
    @Expose
    var id: Int? = null,
    @SerializedName("runner_id")
    @Expose
    var runnerId: String? = null,
    @SerializedName("actions")
    @Expose
    var actions: String? = null,
    @SerializedName("status")
    @Expose
    var status: String? = null,
    @SerializedName("odds")
    @Expose
    var odds: Double? = null,
    @SerializedName("stake")
    @Expose
    var stake: Int? = null,
    @SerializedName("info")
    @Expose
    var info: String? = null,
    @SerializedName("heroic_market_type")
    @Expose
    var heroicMarketType: String? = null,
    @SerializedName("runs")
    @Expose
    var runs: Int? = null,
    @SerializedName("action")
    @Expose
    var action: String? = null,
    @SerializedName("session_id")
    @Expose
    var sessionId: String? = null

) {

    fun getMatchWinnerMode(): String =
        if (actions.equals(ConstantsBase.BACK, true)) ConstantsBase.LAGAI else ConstantsBase.KHAI

    fun getSessionMode(): String {
        return if (actions.equals(ConstantsBase.BACK, true)) ConstantsBase.YES.toLowerCase(
            Locale.US
        )
        else ConstantsBase.NO.toLowerCase(
            Locale.US
        )
    }
}