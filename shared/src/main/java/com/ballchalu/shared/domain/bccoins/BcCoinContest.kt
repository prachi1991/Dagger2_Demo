package com.ballchalu.shared.domain.bccoins

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BcCoinContest(
    var bc_coins: Double? = 0.0,
    var cost: Double? = 0.0,
    var id: Int? = 0,
    var title: String? = ""
): Parcelable