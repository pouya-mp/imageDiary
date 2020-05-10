package com.pouyaa.imagediary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pouyaa.imagediary.utils.CustomStopwatch

class StopwatchViewModel : ViewModel() {

    companion object {
        private const val STARTING_TIME = 0
    }

    private val stopWatch = CustomStopwatch.Factory().apply {
        setStopAt(10)
        setDelegate(object : CustomStopwatch.TickInterface {
            override fun stopwatchDidTick(seconds: Int) {
                _secondsPassed.postValue(seconds)
            }
        })
    }.build()


    private val _secondsPassed = MutableLiveData(STARTING_TIME)
    val secondsPassed: LiveData<Int>
        get() = _secondsPassed

    fun didClickStartButton() {
        stopWatch.start()
    }

    fun didClickStopButton() {
        stopWatch.stop()
    }

}