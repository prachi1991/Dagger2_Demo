package com.ballchalu.shared.domain.winner

import com.google.gson.annotations.SerializedName

data class Meta(

    @field:SerializedName("next_page")
    val nextPage: Any? = null,

    @field:SerializedName("total")
    val total: Int? = null,

    @field:SerializedName("total_pages")
    val totalPages: Int? = null,

    @field:SerializedName("prev_page")
    val prevPage: Any? = null,

    @field:SerializedName("current_page")
    val currentPage: Int? = null
)