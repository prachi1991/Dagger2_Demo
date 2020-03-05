package com.ballchalu.ui.match.details

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ballchalu.base.BaseViewModel
import com.ccpp.shared.core.result.Event
import com.ccpp.shared.core.result.Results
import com.ccpp.shared.database.prefs.SharedPreferenceStorage
import com.ccpp.shared.domain.match_details.MatchDetailsRes
import com.ccpp.shared.network.repository.MatchDetailsRepository
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
        }
    }
}
