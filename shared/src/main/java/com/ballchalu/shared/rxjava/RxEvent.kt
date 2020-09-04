package com.ballchalu.shared.rxjava

import android.graphics.Bitmap
import com.ballchalu.shared.domain.contest.UserContest

 class RxEvent {

    class BcCoin(val userContest: UserContest)
    class UpdateProfile(val bitmap: Bitmap)


}