package com.ccpp.shared.domain

import com.google.gson.annotations.SerializedName

data class MatchListingItem(

	@field:SerializedName("match")
	val match: MatchListing? = null
)