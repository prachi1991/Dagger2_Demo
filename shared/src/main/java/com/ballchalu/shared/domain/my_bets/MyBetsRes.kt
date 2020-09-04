package com.ballchalu.shared.domain.my_bets

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class MyBetsRes (
    @SerializedName("user_bets")
    @Expose
    var userBets: List<UserBet>? = null

)