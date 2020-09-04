package com.ballchalu.ui.match_listing.recent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ballchalu.base.BaseViewModel
import com.ballchalu.shared.core.result.Event
import com.ballchalu.shared.core.result.Results
import com.ballchalu.shared.domain.MatchListingItem
import com.ballchalu.shared.domain.MatchListingRes
import com.ballchalu.shared.network.repository.MatchesRepository
import com.ballchalu.shared.util.StringHelpers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class DeclaredMatchViewModel @Inject constructor(private val matchesRepository: MatchesRepository) :
    BaseViewModel() {

    private val _matchListObserver = MutableLiveData<Event<List<MatchListingItem>>>()
    val matchListObserver: LiveData<Event<List<MatchListingItem>>> = _matchListObserver

    fun callMatchListing(event_type: String, play_status: String) {
        loading.postValue(Event(true))
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = matchesRepository.getMatchesListing(event_type, play_status)) {
                is Results.Success -> handleSuccess(result.data)
                is Results.Error -> failure.postValue(Event(result.exception.message.toString()))
            }
            loading.postValue(Event(false))
        }
    }

    private fun handleSuccess(data: MatchListingRes) {
        data.matches?.sortedWith(Comparator { s1: MatchListingItem, s2: MatchListingItem ->
            StringHelpers.parseMatchDate(s2.match?.startTime!!)
                .compareTo(StringHelpers.parseMatchDate(s1.match?.startTime!!))
        })?.let {
            _matchListObserver.postValue(Event(it))
        }

    }
}