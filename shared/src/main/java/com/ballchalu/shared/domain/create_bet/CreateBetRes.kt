package com.ballchalu.shared.domain.create_bet

import com.ballchalu.shared.domain.contest.UserContest
import com.ballchalu.shared.domain.position.PositionMarketItem
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class CreateBetRes(
    @SerializedName("user_contest")
    @Expose
    var userContest: UserContest? = null,

    @SerializedName("market_position")
    @Expose
    var marketPosition: List<PositionMarketItem>? = null,

    @SerializedName("status")
    @Expose
    var status: String? = null,

    @SerializedName("message")
    @Expose
    var message: String? = null

)