package com.ccpp.shared.domain.bccoins

data class Meta(
    val current_page: Int,
    val next_page: Int,
    val prev_page: Any,
    val total: Int,
    val total_pages: Int
)