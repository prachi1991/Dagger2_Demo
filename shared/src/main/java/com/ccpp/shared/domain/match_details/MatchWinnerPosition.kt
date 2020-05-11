package com.ccpp.shared.domain.match_details


data class MatchWinnerPosition(
    var batTeamRunner: Runner? = null,
    var bwlTeamRunner: Runner? = null,
    var drawTeamRunner: Runner? = null

)