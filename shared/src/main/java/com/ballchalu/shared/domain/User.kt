package com.ballchalu.shared.domain

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(

    @SerializedName("password")
    val password: String? = null,

    @SerializedName("user_name")
    val userName: String? = null,

    @SerializedName("last_name")
    val lastName: String? = null,

    @SerializedName("first_name")
    val firstName: String? = null,

    @SerializedName("email")
    val email: String? = null,

    @SerializedName("confirm_password")
    val confirmPassword: String? = null
) : Parcelable