package com.pouyaa.imagediary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pouyaa.imagediary.ui.fragments.InputTimeForStopwatchFragmentDirections

class InputTimeForStopwatchViewModel : ViewModel() {

    private var _check = MutableLiveData(false)
    val check: LiveData<Boolean>
        get() = _check

    private var action: InputTimeForStopwatchFragmentDirections.ActionInputTimeForStopwatchFragmentToStopwatchFragment? =
        null

    private fun checkText(str: String) {

        if (str.isNotBlank()) {
            val seconds = str.toInt()
            if (seconds > 0) {
                action =
                    InputTimeForStopwatchFragmentDirections.actionInputTimeForStopwatchFragmentToStopwatchFragment()
                action!!.time = seconds
                _check.postValue(true)

            }

        } else {
            _check.postValue(false)
        }

    }

    fun start(): InputTimeForStopwatchFragmentDirections.ActionInputTimeForStopwatchFragmentToStopwatchFragment? {
        action.let {
            return it
        }
    }

    fun changeDetected(str: String) {
        checkText(str)
    }

}
