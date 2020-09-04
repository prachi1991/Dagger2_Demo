package com.ballchalu.shared.domain.contest

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Match (
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("start_time")
    var startTime: String? = null,
    @SerializedName("provider_id")
    var providerId: Int? = null
) : Serializable