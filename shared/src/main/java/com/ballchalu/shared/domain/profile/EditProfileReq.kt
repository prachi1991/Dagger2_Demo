package com.ballchalu.shared.domain.profile


import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@Parcelize
data class EditProfileReq(
    @SerializedName("first_name")
    var firstName: String?,
    @SerializedName("last_name")
    var lastName: String?,
    @SerializedName("email")
    var email: String?,
    @SerializedName("user_name")
    var userName: String?,
    @SerializedName("image")
    var image: String? = null
) : Parcelable