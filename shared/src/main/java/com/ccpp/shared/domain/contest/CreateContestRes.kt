package com.ccpp.shared.domain.contest

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class CreateContestRes (
    @SerializedName("user_contest")
    @Expose
    var userContest: UserContest? = null
){

}