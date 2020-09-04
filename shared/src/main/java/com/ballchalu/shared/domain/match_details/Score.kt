package com.ballchalu.shared.domain.match_details


import com.google.gson.annotations.SerializedName


data class Score(

    @field:SerializedName("batteamruns")
    val batteamruns: Int? = null,

    @field:SerializedName("batteamname")
    val batteamname: String? = null,

    @field:SerializedName("batteamovers")
    val batteamovers: String? = null,

    @field:SerializedName("bwlteamdesc")
    val bwlteamdesc: String? = null,

    @field:SerializedName("batteamdesc")
    val batteamdesc: String? = null,

    @field:SerializedName("bwlteamname")
    val bwlteamname: String? = null,

    @field:SerializedName("batteamwkts")
    val batteamwkts: Int? = null,

    @field:SerializedName("event")
    val event: String? = null,

    @field:SerializedName("status")
    val status: String? = null
)