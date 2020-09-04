package com.ballchalu.shared.domain.profile


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ChangePasswordRes(
    @SerializedName("access_token")
    var accessToken: String?,
    @SerializedName("expires_in")
    var expiresIn: Int?,
    @SerializedName("refresh_token")
    var refreshToken: String?,
    @SerializedName("token_type")
    var tokenType: String?,

    @SerializedName("success")
    var success: Boolean? = false,

    @SerializedName("message")
    var message: String?


) : Parcelable