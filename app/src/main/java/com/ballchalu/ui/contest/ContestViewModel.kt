package com.ballchalu.ui.contest

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ballchalu.base.BaseViewModel
import com.ballchalu.shared.core.result.Event
import com.ballchalu.shared.core.result.Results
import com.ballchalu.shared.domain.MatchListing
import com.ballchalu.shared.domain.contest.Contest
import com.ballchalu.shared.domain.contest.CreateContestRes
import com.ballchalu.shared.domain.contest.MatchContestRes
import com.ballchalu.shared.domain.contest.UserMatchContestRes
import com.ballchalu.shared.domain.user.UserData
import com.ballchalu.shared.domain.user.UserRes
import com.ballchalu.shared.network.repository.ContestRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ContestViewModel @Inject constructor(
    private val contestRepository: ContestRepository,
    val context: Context
) : BaseViewModel() {

     lateinit var contestModel: Contest
    var allContest = 0
    var myContest = 0
var  availablecoins:String?=null
    var matchItem: MatchListing? = null
    var isDeclared: Boolean = false
    var isMatchStarted: Boolean = false

    private val _matchContestResult = MutableLiveData<Event<MatchContestRes>>()
    val matchContestResult: LiveData<Event<MatchContestRes>> = _matchContestResult
    private val _userDetails = MutableLiveData<Event<UserData?>>()
    var userDetails: MutableLiveData<Event<UserData?>> = _userDetails

    fun getAllMatchesContest() {
        if (matchItem?.id == null) return
        loading.postValue(Event(true))
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = contestRepository.getMatchContest(matchItem?.id?.toString()!!)) {
                is Results.Success -> handleSuccess(result.data)
                is Results.Error -> failure.postValue(Event(result.exception.message.toString()))
            }
            loading.postValue(Event(false))
        }
    }
    private fun handleUserSuccess(data: UserRes) {
        _userDetails.postValue(Event(data.user))
    }

    private fun handleSuccess(result: MatchContestRes) {
        _matchContestResult.postValue(Event(result))
    }
    fun callUserDetails() {
        loading.postValue(Event(true))
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = contestRepository.getUserDetails()) {
                is Results.Success -> handleUserSuccess(result.data)
                is Results.Error -> failure.postValue(Event(result.exception.message.toString()))
            }
            loading.postValue(Event(false))
        }
    }
    /*------------------------------------------------------------------End All Match Contest--------------------------------------------------------------*/

    private val _matchUserContestResult = MutableLiveData<Event<UserMatchContestRes>>()
    val matchUserContestResult: LiveData<Event<UserMatchContestRes>> = _matchUserContestResult

    fun getUserMatchesContest() {
        if (matchItem?.id == null) return
        loading.postValue(Event(true))
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = contestRepository.getUserMatchContest(matchItem?.id?.toString()!!)) {
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
                is Results.Error -> handleFailure(result.exception)
            }
            loading.postValue(Event(false))
        }
    }

    private fun handleCreateContestSuccess(result: CreateContestRes) {
        _createContestResult.postValue(Event(result))
    }

    private fun handleFailure(result: Exception) {
        failure.postValue(Event(result.message.toString()))
    }

    /*------------------------------------------------------------------End Create Match Contest--------------------------------------------------------------*/

}
