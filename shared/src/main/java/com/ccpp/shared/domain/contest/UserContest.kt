package com.ccpp.shared.domain.contest

import com.ccpp.shared.domain.user.UserData
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
    var availableCoins: String? = null,
    @SerializedName("contest")
    @Expose
    var contest: Contest? = null,
    @SerializedName("user")
    @Expose
    var user: UserData? = null

    ){

}