package com.ccpp.shared.domain.contest

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class User (
    @SerializedName("id")
    @Expose
    var id: Int? = null,

    @SerializedName("email")
    @Expose
    var email: String? = null,

    @SerializedName("coins")
    @Expose
    var coins: Double? = null,

    @SerializedName("user_name")
    @Expose
    var userName: String? = null,

    @SerializedName("bc_coins")
    @Expose
    var bcCoins: Double? = null,

    @SerializedName("authentication_token")
    @Expose
    var authenticationToken: String? = null,

    @SerializedName("available_coins")
    @Expose
    var availableCoins: Double? = null

){

}