package com.ccpp.shared.domain.match_details


import com.google.gson.annotations.SerializedName


data class SessionsItem(

    @field:SerializedName("session")
    var session: Session? = null

) : Comparable<SessionsItem> {

    override fun compareTo(other: SessionsItem): Int {
        return this.session?.overs?.let { other.session?.overs?.compareTo(it) } ?: 0
    }
}