package com.ballchalu.shared.domain.user

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserData(
    @SerializedName("authentication_token")
    val authentication_token: String? = null,
    @SerializedName("available_coins")
    val available_coins: Double? = 0.0,
    val bc_coins: Double? = 0.0,
    @SerializedName("coins")
    val coins: Double? = 0.0,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("first_name")
    val firstName: String? = "",
    @SerializedName("last_name")
    val lastName: String? = "",
    @SerializedName("image_url")
    val profileUrl: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("user_name")
    val user_name: String? = null
): Parcelable