package com.ccpp.shared.domain.create_bet

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class CreateBetRes (
    @SerializedName("status")
    @Expose
    var status: String? = null,

    @SerializedName("message")
    @Expose
    var message: String? = null

)