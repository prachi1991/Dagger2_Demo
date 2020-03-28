package com.ballchalu.utils

import android.widget.TextView
import com.ballchalu.application.App
import com.ccpp.shared.util.ConstantsBase

object StringUtils {

    fun getString(id: Int, arg: String): String? {
        return App.instance.resources.getString(id, arg)
    }

    fun setEvents(event: String, textView: TextView?) {
        when (event) {
            ConstantsBase.WICKET -> {
                textView?.text = event
            }
            ConstantsBase.WICKET1 -> {
                textView?.text = "Wicket 1"
            }
            ConstantsBase.WICKET2 -> {

                textView?.text = "Wicket 2"
            }
            ConstantsBase.WICKET3 -> {

                textView?.text = "Wicket 3"
            }
            ConstantsBase.WIDE_WICKET -> {
                textView?.text = "Wide Wicket"
            }
            ConstantsBase.BALL_START -> {
                textView?.text = event
            }
            ConstantsBase.THIRD_UMPIRE -> {

                textView?.text = event
            }
            ConstantsBase.PLAYER_INJURED -> {
                textView?.text = "Player Injured"

            }
            ConstantsBase.DOT -> {
                textView?.text = "No Run"

            }
            ConstantsBase.RUN1 -> {
                textView?.text = "1"

            }
            ConstantsBase.RUN2 -> {
                textView?.text = "2"

            }
            ConstantsBase.RUN3 -> {
                textView?.text = "3"
            }
            ConstantsBase.RUN5 -> {
                textView?.text = "5"
            }
            ConstantsBase.FOUR -> {

                textView?.text = "Bet Closed"
            }
            ConstantsBase.SIX -> {

                textView?.text = event
            }
            ConstantsBase.FREE_HIT -> {

                textView?.text = "Free Hit"
            }
            ConstantsBase.NOT_OUT -> {

                textView?.text = "Not Out"
            }
            ConstantsBase.WIDE -> {

                textView?.text = "Wide"
            }
            ConstantsBase.WIDE1 -> {

                textView?.text = "Wide 1"
            }
            ConstantsBase.WIDE2 -> {

                textView?.text = "Wide  2"
            }
            ConstantsBase.WIDE3 -> {

                textView?.text = " Wide 3"
            }
            ConstantsBase.WIDE4 -> {

                textView?.text = "Wide 4"
            }
            ConstantsBase.NO_BALL -> {
                textView?.text = "No Ball"
            }
            ConstantsBase.NO_BALL1 -> {

                textView?.text = "No Ball 1"
            }
            ConstantsBase.NO_BALL2 -> {

                textView?.text = "No Ball 2"
            }
            ConstantsBase.NO_BALL3 -> {

                textView?.text = "No Ball 3"
            }
            ConstantsBase.NO_BALL4 -> {

                textView?.text = "No Ball 4"
            }
            ConstantsBase.NO_BALL5 -> {
                textView?.text = "No Ball 5"
            }
            ConstantsBase.NO_BALL6 -> {

                textView?.text = "No Ball 6"

            }
            else -> {
                textView?.text = event

            }
        }
    }

}