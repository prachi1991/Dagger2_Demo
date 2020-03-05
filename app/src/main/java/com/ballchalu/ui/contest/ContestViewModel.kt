package com.ballchalu.ui.contest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ballchalu.base.BaseViewModel
import com.ccpp.shared.core.result.Event
import com.ccpp.shared.core.result.Results
import com.ccpp.shared.domain.ForgetPassRes
import com.ccpp.shared.domain.LoginRes
import com.ccpp.shared.domain.SignUpReq
import com.ccpp.shared.domain.contest.MatchContestRes
import com.ccpp.shared.network.repository.ContestRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ContestViewModel @Inject constructor(
    val contestRepository: ContestRepository
) : BaseViewModel() {

    private val _matchContestResult = MutableLiveData<Event<MatchContestRes>>()
    val matchContestResult: LiveData<Event<MatchContestRes>> = _matchContestResult

    fun callSignUp(Id: String) {
        loading.postValue(Event(true))
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = contestRepository.getMatchContest(Id)) {
                is Results.Success -> handleSuccess(result.data)
                is Results.Error -> failure.postValue(Event(result.exception.message.toString()))
            }
            loading.postValue(Event(false))
        }
    }

    private fun handleSuccess(result: MatchContestRes) {
        _matchContestResult.postValue(Event(result))
    }

}
