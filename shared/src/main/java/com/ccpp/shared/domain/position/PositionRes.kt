package com.ccpp.shared.domain.position


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PositionRes(

    @field:SerializedName("positions")
    val positions: Positions? = null
) : Parcelable