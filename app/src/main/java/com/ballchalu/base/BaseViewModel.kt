package com.ballchalu.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ballchalu.shared.core.result.Event

 open class BaseViewModel : ViewModel() {

    var failure: MutableLiveData<Event<String>> = MutableLiveData()
    var loading: MutableLiveData<Event<Boolean>> = MutableLiveData()


}