package com.ccpp.shared.domain.bccoins

import android.os.Parcelable
import com.ccpp.shared.util.StringHelpers
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BcCoinsLedgerData(
    val amount: Double? = 0.0,
    val balance: String? = null,
    val created_at: String? = null,
    val entity_id: Int? = 0,
    val entity_type: String? = null,
    val type: String? = null,
    val id: Int? = 0,
    val note: String? = null,
    val updated_at: String? = null,
    val user_id: Int? = 0
) : Parcelable {
    fun getDate() = created_at?.let { StringHelpers.parseFullData(it) }

    fun getContestName() = type ?: ""

    fun getCredit(): String = if (amount ?: 0.0 > 0) amount.toString() else ""
    fun getDebit(): String = if (amount ?: 0.0 < 0) amount.toString() else ""
}