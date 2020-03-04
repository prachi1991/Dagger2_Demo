package com.ccpp.shared.domain

import com.google.gson.annotations.SerializedName

data class ForgetPassRes(

    @SerializedName("token_status")
    val token_status: Boolean? = false,

    @SerializedName("token_status")
    val password_token: String? = null
)