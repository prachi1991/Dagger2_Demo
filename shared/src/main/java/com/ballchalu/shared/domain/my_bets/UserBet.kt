package com.ballchalu.shared.domain.my_bets

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class UserBet (
    @SerializedName("user_bet")
    @Expose
    var userBet: UserMyBet? = null
)