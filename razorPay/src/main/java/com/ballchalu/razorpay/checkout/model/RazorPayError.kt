package com.ballchalu.razorpay.checkout.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RazorPayError(
    @SerializedName("error")
    var error: Error?,
    @SerializedName("http_status_code")
    var httpStatusCode: Int?
) : Parcelable