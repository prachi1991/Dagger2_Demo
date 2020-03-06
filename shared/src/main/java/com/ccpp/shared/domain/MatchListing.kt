package com.ccpp.shared.domain

import com.google.gson.annotations.SerializedName

data class MatchListing(

	@field:SerializedName("start_time")
	val startTime: String? = null,

	@field:SerializedName("is_join")
	val isJoin: Boolean? = null,

	@field:SerializedName("provider_id")
	val providerId: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("tournament")
	val tournament: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("provider_name")
	val providerName: String? = null
)