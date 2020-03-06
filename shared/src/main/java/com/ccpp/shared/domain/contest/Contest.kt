package com.ccpp.shared.domain.contest

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Contest(
    @SerializedName("id")
    @Expose
    val id: Int? = 0,

    @SerializedName("title")
    @Expose
    val title: String?,

    @SerializedName("credit_coins")
    @Expose
    val creditCoins: Int? = 0,

    @SerializedName("fess")
    @Expose
    val fess: Int? = 0,

    @SerializedName("spots")
    @Expose
    val spots: Int? = 0,

    @SerializedName("winners")
    @Expose
    val winners: Int? = 0,

    @SerializedName("price_pool")
    @Expose
    val pricePool: Int? = 0,

    @SerializedName("available_spots")
    @Expose
    val availableSpots: Int? = 0,

    @SerializedName("match")
    @Expose
    val match: Match,

    @SerializedName("is_participated")
    @Expose
    val isParticipated: Boolean = false
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