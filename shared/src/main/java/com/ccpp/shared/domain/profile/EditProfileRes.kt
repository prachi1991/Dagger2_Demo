package com.ccpp.shared.domain.profile


import android.os.Parcelable
import com.ccpp.shared.domain.User
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EditProfileRes(
    @SerializedName("success")
    var success: Boolean = false,
    @SerializedName("message")
    var message: String?,
    @SerializedName("user")
    var user: User? = null
) : Parcelable

