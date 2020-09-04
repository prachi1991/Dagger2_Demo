package com.ballchalu.shared.domain

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MatchListingItem(

    @SerializedName("match")
    val match: MatchListing? = null
) : Serializable