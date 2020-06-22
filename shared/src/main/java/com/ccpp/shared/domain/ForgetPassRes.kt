package com.ccpp.shared.domain

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class
ForgetPassRes(

    @SerializedName("token_status")
    val token_status: Boolean? = false,
    @SerializedName("password_token")
    val password_token: String? = null,

    @SerializedName("error")
    val error: String? = null
) : Parcelable