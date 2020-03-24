package com.ccpp.shared.domain.bccoins

data class BcCoinsLedgerData(
    val amount: Double,
    val balance: Double,
    val created_at: String,
    val entity_id: Int,
    val entity_type: String,
    val id: Int,
    val note: String,
    val updated_at: String,
    val user_id: Int
)