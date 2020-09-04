package com.ballchalu.shared.domain.create_bet

import com.ballchalu.shared.domain.contest.UserContest

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class CreateSessionBetRes (
    @SerializedName("user_contest")
    @Expose
    var userContest: UserContest? = null,
    @SerializedName("session_position")
    @Expose
    var sessionPosition: Double? = null,
    @SerializedName("status")
    @Expose
    var status: String? = null,
    @SerializedName("message")
    @Expose
    var message: String? = null

)