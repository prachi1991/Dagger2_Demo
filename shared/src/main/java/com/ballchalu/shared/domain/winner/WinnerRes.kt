package com.ballchalu.shared.domain.winner

import com.google.gson.annotations.SerializedName

data class WinnerRes(

	@field:SerializedName("ranks")
	val ranks: List<RanksItem>? = null,

	@field:SerializedName("meta")
	val meta: Meta? = null
)