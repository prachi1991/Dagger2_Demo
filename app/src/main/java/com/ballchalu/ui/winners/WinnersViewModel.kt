package com.ballchalu.ui.winners

import com.ballchalu.base.BaseViewModel
import com.ccpp.shared.domain.winner.WinnerRes
import javax.inject.Inject

class WinnersViewModel @Inject constructor(

): BaseViewModel()
{

    fun winnerList():ArrayList<WinnerRes>
    {
        val arrayList:ArrayList<WinnerRes> = arrayListOf()

        arrayList.add(WinnerRes("1st","Jhon Wick","2,75,000"))
        arrayList.add(WinnerRes("2nd","Gram Cracker","1,00,000"))
        arrayList.add(WinnerRes("3rd","Pat Agonia","75,000"))
        arrayList.add(WinnerRes("4th","Matt Innae","50,000"))
        arrayList.add(WinnerRes("5th","Sam Parker","10,000"))

        return arrayList
    }

}