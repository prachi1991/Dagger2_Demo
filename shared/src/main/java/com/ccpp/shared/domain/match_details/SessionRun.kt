package com.ccpp.shared.domain.match_details


import com.google.gson.annotations.SerializedName


data class SessionRun(

    @field:SerializedName("no_run")
    val noRun: String? = null,

    @field:SerializedName("yes_rate")
    val yesRate: String? = null,

    @field:SerializedName("session_id")
    val sessionId: Int? = null,

    @field:SerializedName("no_rate")
    val noRate: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("yes_run")
    val yesRun: String? = null
)