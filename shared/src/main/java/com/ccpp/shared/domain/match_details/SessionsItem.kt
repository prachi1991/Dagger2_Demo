package com.ccpp.shared.domain.match_details


import com.google.gson.annotations.SerializedName


data class SessionsItem(

    @field:SerializedName("session")
    var session: Session? = null

) : Comparable<SessionsItem> {

    override fun compareTo(other: SessionsItem): Int {
        return other.session?.overs?.let { this.session?.overs?.compareTo(it) } ?: 0
    }
}