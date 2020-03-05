package com.ccpp.shared.domain.match_details


import com.google.gson.annotations.SerializedName


data class Match(

    @field:SerializedName("venue")
    val venue: String? = null,

    @field:SerializedName("heroic_commentary")
    val heroicCommentary: HeroicCommentary? = null,

    @field:SerializedName("team1")
    val team1: String? = null,

    @field:SerializedName("team2")
    val team2: String? = null,

    @field:SerializedName("event_sub_type")
    val eventSubType: String? = null,

    @field:SerializedName("tournament")
    val tournament: Any? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("betfair_event_id")
    val betfairEventId: String? = null,

    @field:SerializedName("score")
    val score: Score? = null,

    @field:SerializedName("start_time")
    val startTime: String? = null,

    @field:SerializedName("event_type")
    val eventType: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("sequence_string")
    val sequenceString: String? = null,

    @field:SerializedName("play_status")
    val playStatus: String? = null
)