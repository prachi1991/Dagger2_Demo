package com.ccpp.shared.domain.create_bet

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BetDetailsBundle(
    val betSessionReq: CreateSessionBetReq? = null,
    val betReq: CreateBetReq? = null,
    val isCountMultiply: Boolean? = true
) : Parcelable