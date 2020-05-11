package com.pouyaa.imagediary.viewmodel

import android.text.format.DateUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.pouyaa.imagediary.utils.CustomStopwatch

class StopwatchViewModel(private val stopAt: Int) : ViewModel() {

    companion object {
        private const val STARTING_TIME = 0
    }

    private val stopWatch = CustomStopwatch.Factory().apply {
        setStopAt(stopAt)
        setDelegate(object : CustomStopwatch.TickInterface {
            override fun stopwatchDidTick(seconds: Int) {
                _secondsPassed.postValue(seconds)
            }
        })
    }.build()

    init {
        stopWatch.start()
    }

    private val _secondsPassed = MutableLiveData(STARTING_TIME)
    val secondsPassed: LiveData<Int>
        get() = _secondsPassed

    val formattedSeconds = Transformations.map(secondsPassed) {
        DateUtils.formatElapsedTime(it.toLong())
    }


    fun didClickStopButton() {
        stopWatch.stop()
    }

}