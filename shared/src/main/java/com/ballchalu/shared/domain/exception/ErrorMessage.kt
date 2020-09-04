package com.ballchalu.shared.domain.exception

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ErrorMessage(
    @SerializedName("email")
    var email: ArrayList<String>? = null,
    @SerializedName("error")
    var errors: String? = null
) : Parcelable, Exception() {
    override fun toString(): String {
        return super.toString()
    }
}