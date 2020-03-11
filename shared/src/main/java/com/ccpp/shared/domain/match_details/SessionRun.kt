package com.ccpp.shared.domain.match_details


import com.google.gson.annotations.SerializedName


data class SessionRun(

    @field:SerializedName("no_run")
    var noRun: String? = null,

    @SerializedName("yes_rate")
    val yesRate: String? = null,

    @SerializedName("session_id")
    val sessionId: Int? = null,

    @SerializedName("no_rate")
    val noRate: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("yes_run")
    var yesRun: String? = null
)