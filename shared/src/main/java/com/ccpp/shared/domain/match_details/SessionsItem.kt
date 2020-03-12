package com.ccpp.shared.domain.match_details


import com.google.gson.annotations.SerializedName


data class SessionsItem(

    @field:SerializedName("session")
    var session: Session? = null
)