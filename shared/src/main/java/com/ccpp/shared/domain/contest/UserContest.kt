package com.ccpp.shared.domain.contest

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class UserContest (
    @SerializedName("id")
    @Expose
    var id: Int? = null,
    @SerializedName("credited_coins")
    @Expose
    var creditedCoins: Int? = null,
    @SerializedName("winned_coins")
    @Expose
    var winnedCoins: Any? = null,
    @SerializedName("available_coins")
    @Expose
    var availableCoins: Int? = null,
    @SerializedName("contest")
    @Expose
    var contest: Contest? = null,
    @SerializedName("user")
    @Expose
    var user: User? = null

    ){

}