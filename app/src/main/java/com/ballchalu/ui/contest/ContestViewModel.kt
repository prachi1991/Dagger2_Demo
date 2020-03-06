package com.ballchalu.ui.contest

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ballchalu.base.BaseViewModel
import com.ccpp.shared.core.result.Event
import com.ccpp.shared.core.result.Results
import com.ccpp.shared.domain.ForgetPassRes
import com.ccpp.shared.domain.LoginRes
import com.ccpp.shared.domain.SignUpReq
import com.ccpp.shared.domain.contest.CreateContestRes
import com.ccpp.shared.domain.contest.MatchContestRes
import com.ccpp.shared.domain.contest.UserMatchContestRes
import com.ccpp.shared.network.repository.ContestRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ContestViewModel @Inject constructor(
    val contestRepository: ContestRepository,
    val context: Context
) : BaseViewModel() {

    private val _matchContestResult = MutableLiveData<Event<MatchContestRes>>()
    val matchContestResult: LiveData<Event<MatchContestRes>> = _matchContestResult

    fun getAllMatchesContest(Id: String) {
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

    /*------------------------------------------------------------------End All Match Contest--------------------------------------------------------------*/

    private val _matchUserContestResult = MutableLiveData<Event<UserMatchContestRes>>()
    val matchUserContestResult: LiveData<Event<UserMatchContestRes>> = _matchUserContestResult

    fun getUserMatchesContest(Id: String) {
        loading.postValue(Event(true))
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = contestRepository.getUserMatchContest(Id)) {
                is Results.Success -> handleUserContestSuccess(result.data)
                is Results.Error -> failure.postValue(Event(result.exception.message.toString()))
            }
            loading.postValue(Event(false))
        }
    }

    private fun handleUserContestSuccess(result: UserMatchContestRes) {
        _matchUserContestResult.postValue(Event(result))
    }

    /*------------------------------------------------------------------End User Match Contest--------------------------------------------------------------*/


    private val _createContestResult = MutableLiveData<Event<CreateContestRes>>()
    val createContestResult: LiveData<Event<CreateContestRes>> = _createContestResult

    fun createUserMatchContest(Id: String) {
        loading.postValue(Event(true))
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = contestRepository.createUserMatchContest(Id)) {
                is Results.Success -> handleCreateContestSuccess(result.data)
                is Results.Error -> failure.postValue(Event(result.exception.message.toString()))
            }
            loading.postValue(Event(false))
        }
    }

    private fun handleCreateContestSuccess(result: CreateContestRes) {
        _createContestResult.postValue(Event(result))
    }

    /*------------------------------------------------------------------End Create Match Contest--------------------------------------------------------------*/

}
