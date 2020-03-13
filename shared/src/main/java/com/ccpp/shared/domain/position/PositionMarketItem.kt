package com.ccpp.shared.domain.position


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PositionMarketItem(

    @field:SerializedName("heroic_market_type")
    val heroicMarketType: String? = null,

    @field:SerializedName("market_id")
    val marketId: Int? = null,

    @field:SerializedName("runners")
    val runners: List<PositionRunnersItem?>? = null
) : Parcelable