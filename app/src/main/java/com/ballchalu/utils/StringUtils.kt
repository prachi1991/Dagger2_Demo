package com.ballchalu.utils

import android.widget.TextView
import com.ballchalu.application.App
import com.ccpp.shared.util.ConstantsBase
import java.util.*

object StringUtils {
    fun getString(id: Int, arg: String): String? {
        return App.instance.resources.getString(id, arg)
    }

    fun setEvents(event: String, textView: TextView?) {
        when (event.toLowerCase(Locale.getDefault())) {
            ConstantsBase.Event_WICKET -> {
                textView?.text = "Out"
            }
            ConstantsBase.Event_WICKET1 -> {
                textView?.text = "Out 1"
            }
            ConstantsBase.Event_WICKET2 -> {
                textView?.text = "Out 2"
            }
            ConstantsBase.Event_WICKET3 -> {
                textView?.text = "Out 3"
            }
            ConstantsBase.Event_WIDE_WICKET -> {
                textView?.text = "Wide & OUT"
            }
            ConstantsBase.Event_BALL_START -> {
                textView?.text = "Ball Start"
            }
            ConstantsBase.Event_THIRD_UMPIRE -> {
                textView?.text = "Third Umpire"
            }
            ConstantsBase.Event_PLAYER_INJURED -> {
                textView?.text = "Player Injured"
            }
            ConstantsBase.Event_FREE_HIT -> {
                textView?.text = "Free Hit"
            }
            ConstantsBase.Event_DOT -> {
                textView?.text = "No Run"
            }
            ConstantsBase.Event_RUN1 -> {
                textView?.text = "1"
            }
            ConstantsBase.Event_RUN2 -> {
                textView?.text = "2"
            }
            ConstantsBase.Event_RUN3 -> {
                textView?.text = "3"
            }
            ConstantsBase.Event_RUN5 -> {
                textView?.text = "5"
            }
            ConstantsBase.Event_FOUR -> {
                textView?.text = "4"
            }
            ConstantsBase.Event_SIX -> {
                textView?.text = "6"
            }
            ConstantsBase.Event_NOT_OUT -> {
                textView?.text = "Not Out"
            }
            ConstantsBase.Event_WIDE -> {
                textView?.text = "Wide"
            }
            ConstantsBase.Event_WIDE1 -> {
                textView?.text = "Wide 1"
            }
            ConstantsBase.Event_WIDE2 -> {
                textView?.text = "Wide 2"
            }
            ConstantsBase.Event_WIDE3 -> {
                textView?.text = "Wide 3"
            }
            ConstantsBase.Event_WIDE4 -> {
                textView?.text = "Wide 4"
            }
            ConstantsBase.Event_NO_BALL -> {
                textView?.text = "No Ball"
            }
            ConstantsBase.Event_NO_BALL1 -> {
                textView?.text = "No Ball 1"
            }
            ConstantsBase.Event_NO_BALL2 -> {
                textView?.text = "No Ball 2"
            }
            ConstantsBase.Event_NO_BALL3 -> {
                textView?.text = "No Ball 3"
            }
            ConstantsBase.Event_NO_BALL4 -> {
                textView?.text = "No Ball 4"
            }
            ConstantsBase.Event_NO_BALL5 -> {
                textView?.text = "No Ball 5"
            }
            ConstantsBase.Event_NO_BALL6 -> {
                textView?.text = "No Ball 6"
            }
            else -> {
                textView?.text = event
            }
        }
    }
}