package com.ballchalu.shared.domain.profile


import android.os.Parcelable
import com.ballchalu.shared.domain.User
import com.ballchalu.shared.domain.user.UserData
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EditProfileRes(
    @SerializedName("success")
    var success: Boolean = false,
    @SerializedName("message")
    var message: String?,
    @SerializedName("user")
    var user: UserData? = null
) : Parcelable

