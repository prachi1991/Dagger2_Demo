package com.ballchalu.ui.match.details

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ballchalu.base.BaseViewModel
import com.ccpp.shared.core.result.Event
import com.ccpp.shared.core.result.Results
import com.ccpp.shared.database.prefs.SharedPreferenceStorage
import com.ccpp.shared.domain.match_details.Market
import com.ccpp.shared.domain.match_details.MarketsItem
import com.ccpp.shared.domain.match_details.MatchDetailsRes
import com.ccpp.shared.network.repository.MatchDetailsRepository
import com.ccpp.shared.util.ConstantsBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MatchDetailsViewModel @Inject constructor(
    private val sharedPreferenceStorage: SharedPreferenceStorage,
    private val loginRepository: MatchDetailsRepository,
    private val context: Context
) :
    BaseViewModel() {

    private val _matchResult = MutableLiveData<Event<MatchDetailsRes>>()
    val matchResult: LiveData<Event<MatchDetailsRes>> = _matchResult

    fun callMatchDetailsAsync() {
        loading.postValue(Event(true))
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = loginRepository.callMatchDetailsAsync(57)) {
                is Results.Success -> handleSuccess(result.data)
                is Results.Error -> failure.postValue(Event(result.exception.message.toString()))
            }
            loading.postValue(Event(false))
        }
    }

    private fun handleSuccess(data: MatchDetailsRes?) {
        data?.let {
            _matchResult.postValue(Event(data))
            setMarketData(data.markets)
        }
    }

    private val _winnerMarketEvent = MutableLiveData<Event<Market>>()
    val winnerMarketEvent: LiveData<Event<Market>> = _winnerMarketEvent

    private val _evenOddMarketEvent = MutableLiveData<Event<Market>>()
    val evenOddMarketEvent: LiveData<Event<Market>> = _evenOddMarketEvent

    private val _endingDigitMarketEvent = MutableLiveData<Event<Market>>()
    val endingDigitMarketEvent: LiveData<Event<Market>> = _endingDigitMarketEvent


    private fun setMarketData(marketList: List<MarketsItem>?) {
        marketList?.forEachIndexed { index, marketsItem ->
            when {
                (marketsItem.market?.heroicMarketType?.equals(
                    ConstantsBase.MATCH_WINNER,
                    true
                ) == true
                        && marketsItem.market?.status?.equals(ConstantsBase.open, true) == true
                        ) || marketsItem.market?.status?.equals(ConstantsBase.suspend, true) == true
                -> {
                    marketsItem.market?.let {
                        _winnerMarketEvent.postValue(Event(it))
                    }
                }
                marketsItem.market?.heroicMarketType?.equals(
                    ConstantsBase.EVEN_ODD,
                    true
                ) == true -> {
                    marketsItem.market?.let {
                        _evenOddMarketEvent.postValue(Event(it))
                    }
                }
                marketsItem.market?.heroicMarketType?.equals(
                    ConstantsBase.ENDING_DIGIT,
                    true
                ) == true -> {
                    marketsItem.market?.let {
                        _endingDigitMarketEvent.postValue(Event(it))
                    }
                }
            }

        }
    }

}
