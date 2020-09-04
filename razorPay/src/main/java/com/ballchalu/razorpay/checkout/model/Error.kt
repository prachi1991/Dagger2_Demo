package com.ballchalu.razorpay.checkout.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Error(
    @SerializedName("code")
    var code: String?,
    @SerializedName("description")
    var description: String?,
    @SerializedName("metadata")
    var metadata: Metadata?,
    @SerializedName("reason")
    var reason: String?,
    @SerializedName("source")
    var source: String?,
    @SerializedName("step")
    var step: String?
) : Parcelable