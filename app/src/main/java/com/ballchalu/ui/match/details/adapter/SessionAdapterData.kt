package com.ballchalu.ui.match.details.adapter

import com.ccpp.shared.domain.match_details.Session
import com.ccpp.shared.util.ConstantsBase

data class SessionAdapterData(var session: Session?) {
    val isSuspend = session?.status.equals(ConstantsBase.suspend, true)

    val yesValue =
        if (session?.canYes == true && !isSuspend) session?.sessionRun?.yesRun ?: "" else ""
    val noValue =
        if (session?.canNo == true && !isSuspend) session?.sessionRun?.noRun ?: "" else ""

    val yesRate =
        if (session?.canYes == true && !isSuspend) session?.sessionRun?.yesRate ?: "" else ""
    val noRate = if (session?.canNo == true && !isSuspend) session?.sessionRun?.noRate ?: "" else ""

    val title = if (session?.title?.isEmpty() == true) session?.heroicTitle else session?.title
}