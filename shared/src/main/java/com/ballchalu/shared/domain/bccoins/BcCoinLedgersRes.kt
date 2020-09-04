package com.ballchalu.shared.domain.bccoins

data class BcCoinLedgersRes(
    val bc_coins_ledgers: List<BcCoinsLedgerData>,
    val meta: Meta
)