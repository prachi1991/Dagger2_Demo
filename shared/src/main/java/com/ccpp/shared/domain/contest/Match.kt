package com.ccpp.shared.domain.contest

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class Match (
    @SerializedName("id")
    @Expose
    var id: Int? = null,
    @SerializedName("title")
    @Expose
    var title: String? = null,
    @SerializedName("start_time")
    @Expose
    var startTime: String? = null,
    @SerializedName("provider_id")
    @Expose
    var providerId: String? = null
){

}