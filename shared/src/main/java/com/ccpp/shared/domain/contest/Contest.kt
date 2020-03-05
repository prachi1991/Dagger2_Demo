package com.ccpp.shared.domain.contest

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Contest(
    @SerializedName("id")
    @Expose
    val id: Int,
    
    @SerializedName("title")
    @Expose
    val title: String?,
    
    @SerializedName("credit_coins")
    @Expose
    val creditCoins: Int,

    @SerializedName("fess")
    @Expose
    val fess: Int,

    @SerializedName("spots")
    @Expose
    val spots: Int,

    @SerializedName("winners")
    @Expose
    val winners: Int,

    @SerializedName("price_pool")
    @Expose
    val pricePool: Int,

    @SerializedName("available_spots")
    @Expose
    val availableSpots: Int,

    @SerializedName("match")
    @Expose
    val match: Match,

    @SerializedName("is_participated")
    @Expose
    val isParticipated: Boolean
) {

    public fun getPrice()= "$pricePool/-"

    public fun getEntery()= "$fess/-"

    public fun remainingSpots() = "$availableSpots"

    public fun winners()= "$winners"


    public fun getCredit():String
    {
        return creditCoins.toString()+"/-"
    }

    public fun getTotalSpots():String
    {
        return "TOTAL "+spots+" SPOT"
    }

}