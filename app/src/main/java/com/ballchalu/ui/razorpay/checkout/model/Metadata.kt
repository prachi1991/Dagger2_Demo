package com.ballchalu.ui.razorpay.checkout.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Metadata(
    @SerializedName("payment_id")
    var paymentId: String?
) : Parcelable