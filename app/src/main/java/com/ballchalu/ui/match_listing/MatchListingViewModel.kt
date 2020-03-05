package com.ballchalu.ui.match_listing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ballchalu.base.BaseViewModel
import com.ccpp.shared.core.result.Event
import com.ccpp.shared.core.result.Results
import com.ccpp.shared.domain.LoginRes
import com.ccpp.shared.domain.MatchListingReq
import com.ccpp.shared.domain.MatchListingRes
import com.ccpp.shared.domain.data.LoginFormState
import com.ccpp.shared.network.repository.LoginRepository
import com.ccpp.shared.network.repository.MatchesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MatchListingViewModel  @Inject constructor(private val matchesRepository: MatchesRepository) :
    BaseViewModel() {

    private val _matchListingCall = MutableLiveData<Event<MatchListingRes>>()
    val matchListingCall: LiveData<Event<MatchListingRes>> = _matchListingCall

    fun callMatchListing(event_type: String,play_status: String) {
        if (loading.value?.peekContent() == false) return
        loading.postValue(Event(true))
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = matchesRepository.getMatchesListing(event_type,play_status)) {
                is Results.Success -> _matchListingCall.postValue(Event(result.data))
                is Results.Error -> failure.postValue(Event(result.exception.message.toString()))
            }
            loading.postValue(Event(false))
        }
    }
}