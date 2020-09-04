package com.ballchalu.razorpay.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
public data class PaymentDetailsModel(
    @SerializedName("amount")
    var amount: String? = null,
    @SerializedName("card[cvv]")
    var cardcvv: String? = null,
    @SerializedName("card[expiry_month]")
    var cardexpiryMonth: String? = null,
    @SerializedName("card[expiry_year]")
    var cardexpiryYear: String? = null,
    @SerializedName("card[name]")
    var cardname: String? = null,
    @SerializedName("card[number]")
    var cardnumber: String? = null,
    @SerializedName("contact")
    var contact: String? = null,
    @SerializedName("currency")
    var currency: String = "INR",
    @SerializedName("email")
    var email: String? = null,
    @SerializedName("method")
    var method: String? = null,
    @SerializedName("bank")
    var bank: String? = null

) : Parcelable