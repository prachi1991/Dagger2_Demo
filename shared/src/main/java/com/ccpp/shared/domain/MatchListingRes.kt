package com.ccpp.shared.domain

import com.google.gson.annotations.SerializedName

data class MatchListingRes(

	@field:SerializedName("matches")
	val matches: List<MatchListingItem?>? = null
)