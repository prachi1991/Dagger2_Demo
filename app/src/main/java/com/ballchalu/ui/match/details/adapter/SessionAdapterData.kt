package com.ballchalu.ui.match.details.adapter

import android.view.View
import com.ccpp.shared.domain.match_details.Session
import com.ccpp.shared.util.ConstantsBase

data class SessionAdapterData(var session: Session?) {
    val isSuspend = session?.status.equals(ConstantsBase.suspend, true)

    val getOvers = session?.overs?.let { "$it Overs" }

    val isYesVisible = if (session?.canYes == true) View.VISIBLE else View.GONE
    val isNoVisible = if (session?.canNo == true) View.VISIBLE else View.GONE

    val yesValue =
        if (session?.canYes == true && !isSuspend) session?.sessionRun?.yesRun.toString() else ""
    val noValue =
        if (session?.canNo == true && !isSuspend) session?.sessionRun?.noRun.toString() else ""

    val yesRate = if (!isSuspend) session?.sessionRun?.yesRate ?: "" else ""
    val noRate = if (!isSuspend) session?.sessionRun?.noRate ?: "" else ""

    val title = if (session?.overs == null) session?.heroicTitle?.toString() else getOvers
}