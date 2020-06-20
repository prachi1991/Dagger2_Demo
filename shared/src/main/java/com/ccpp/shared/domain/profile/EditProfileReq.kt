package com.ccpp.shared.domain.profile


import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@Parcelize
data class EditProfileReq(
    @SerializedName("first_name")
    var firstName: String?,
    @SerializedName("last_name")
    var LastName: String?,
    @SerializedName("auth_token")
    var auth_token: String? = ""
) : Parcelable