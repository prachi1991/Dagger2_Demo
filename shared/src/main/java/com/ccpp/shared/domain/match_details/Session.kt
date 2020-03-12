package com.ccpp.shared.domain.match_details


import com.google.gson.annotations.SerializedName


data class Session(

    @field:SerializedName("can_yes")
    val canYes: Boolean = false,

    @field:SerializedName("heroic_title")
    val heroicTitle: String? = null,

    @field:SerializedName("match_id")
    val matchId: Int? = null,

    @field:SerializedName("innings")
    val innings: Int? = null,

    @field:SerializedName("_type")
    val type: String? = null,

    @field:SerializedName("can_no")
    val canNo: Boolean = false,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("team")
    val team: String? = null,

    @field:SerializedName("overs")
    val overs: Int? = null,

    @field:SerializedName("title")
    var title: String? = null,

    @field:SerializedName("session_run")
    val sessionRun: SessionRun? = null,

    @field:SerializedName("status")
    var status: String? = null
)
