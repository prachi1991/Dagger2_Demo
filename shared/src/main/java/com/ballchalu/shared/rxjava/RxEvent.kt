package com.ballchalu.shared.rxjava

import android.graphics.Bitmap
import com.ballchalu.shared.domain.bccoins.buy.BcCoinBuyRes
import com.ballchalu.shared.domain.contest.UserContest

 class RxEvent {

    class BcCoin(val userContest: Any, val action: String)
    class UpdateProfile(val bitmap: Bitmap)


}