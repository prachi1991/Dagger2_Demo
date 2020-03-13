package com.ccpp.shared.domain.position


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PositionRunnersItem(

    @field:SerializedName("runner_id")
    val runnerId: Int? = null,

    @field:SerializedName("runner_position")
    val runnerPosition: Int? = null
) : Parcelable