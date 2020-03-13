package com.ccpp.shared.domain.position


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Positions(

    @field:SerializedName("session_position")
    val sessionPosition: String? = null,

    @field:SerializedName("market_position")
    val marketPosition: List<PositionMarketItem>? = null
) : Parcelable