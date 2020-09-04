package com.ballchalu.mqtt

import com.ballchalu.shared.domain.match_details.Market
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MqttMarket(
    @SerializedName("market")
    var market: Market? = null,
    @SerializedName("action")
    var action: String? = null,
    @SerializedName("type")
    var type: String? = null
) : Serializable
