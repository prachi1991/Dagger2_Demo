package com.ccpp.shared.domain.match_details


import com.google.gson.annotations.SerializedName


data class SessionRun(

    @field:SerializedName("no_run")
    val noRun: Int? = null,

    @field:SerializedName("yes_rate")
    val yesRate: Double? = null,

    @field:SerializedName("session_id")
    val sessionId: Int? = null,

    @field:SerializedName("no_rate")
    val noRate: Double? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("yes_run")
    val yesRun: Int? = null
)