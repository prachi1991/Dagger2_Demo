package com.ballchalu.ui.match_listing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ballchalu.base.BaseViewModel
import com.ccpp.shared.core.result.Event
import com.ccpp.shared.core.result.Results
import com.ccpp.shared.domain.MatchListingRes
import com.ccpp.shared.network.repository.MatchesRepository
import com.ccpp.shared.util.ConstantsBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MatchListingViewModel @Inject constructor(private val matchesRepository: MatchesRepository) :
    BaseViewModel() {

    private val _matchListingObserver = MutableLiveData<Event<MatchListingRes>>()
    val inPlayListEvent: LiveData<Event<MatchListingRes>> = _matchListingObserver

    private val _upcomingmatchListingObserver = MutableLiveData<Event<MatchListingRes>>()
    val upcomingListEvent: LiveData<Event<MatchListingRes>> = _upcomingmatchListingObserver

    fun callMatchListing(event_type: String, play_status: String) {
        loading.postValue(Event(true))
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = matchesRepository.getMatchesListing(event_type, play_status)) {
                is Results.Success -> handleSuccess(result.data, play_status)
                is Results.Error -> failure.postValue(Event(result.exception.message.toString()))
            }
            loading.postValue(Event(false))
        }
    }

    private fun handleSuccess(
        data: MatchListingRes, playStatus: String
    ) {
        if (playStatus == ConstantsBase.IN_PLAY) {
            _matchListingObserver.postValue(Event(data))
        } else if (playStatus == ConstantsBase.UPCOMING) {
            _upcomingmatchListingObserver.postValue(Event(data))
        }
    }
}