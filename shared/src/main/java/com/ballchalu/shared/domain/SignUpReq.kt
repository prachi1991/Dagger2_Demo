package com.ballchalu.shared.domain

import com.google.gson.annotations.SerializedName

data class SignUpReq(

    @SerializedName("user")
    val user: User? = null
)