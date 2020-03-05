package com.ccpp.shared.domain.match_details


import com.google.gson.annotations.SerializedName


data class SessionsItem(

    @field:SerializedName("session")
    val session: Session? = null
)