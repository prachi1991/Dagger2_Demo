package com.ccpp.shared.rxjava

import android.graphics.Bitmap
import com.ccpp.shared.domain.contest.UserContest

class RxEvent {

    class BcCoin(val userContest: UserContest)
    class UpdateProfile(val bitmap: Bitmap)
}