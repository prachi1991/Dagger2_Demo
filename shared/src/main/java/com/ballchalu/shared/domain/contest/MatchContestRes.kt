package com.ballchalu.shared.domain.contest

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MatchContestRes(
    @SerializedName("contests")
    @Expose
    val contests: List<Contest>? = null
){

}