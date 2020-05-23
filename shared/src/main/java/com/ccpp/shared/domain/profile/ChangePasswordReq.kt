package com.ccpp.shared.domain.profile


import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@Parcelize
data class ChangePasswordReq(
    @SerializedName("new_password")
    var newPassword: String?,
    @SerializedName("password")
    var password: String?,
    @SerializedName("userName")
    var userName: String?
) : Parcelable