package com.ballchalu.shared.analytics

import android.app.Activity

/** Analytics API surface */
interface AnalyticsHelper {

    /** Record a event */
    fun sendEvent(name: String)

    /** Record a screen view */
    fun sendScreenView(screenName: String, activity: Activity)

    /** Record a UI event, e.g. user clicks a button */
    fun logUiEvent(itemId: String, action: String)

    /** Set the user signed in property */
    fun setUserSignedIn(isSignedIn: Boolean)

    /** Set the user registered property */
    fun setUserRegistered(isRegistered: Boolean)
}

/** Actions that should be used when sending analytics events */
object AnalyticsActions {
    // UI Actions
    const val STARRED = "Bookmarked"
}
