package com.ballchalu.ui.match_listing.recent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ballchalu.base.BaseViewModel
import com.ccpp.shared.core.result.Event
import com.ccpp.shared.core.result.Results
import com.ccpp.shared.domain.MatchListingRes
import com.ccpp.shared.network.repository.MatchesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class DeclaredMatchViewModel @Inject constructor(private val matchesRepository: MatchesRepository) :
    BaseViewModel() {

    private val _matchListObserver = MutableLiveData<Event<MatchListingRes>>()
    val matchListObserver: LiveData<Event<MatchListingRes>> = _matchListObserver

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

    private fun handleSuccess(data: MatchListingRes, playStatus: String) {
        _matchListObserver.postValue(Event(data))
    }
}