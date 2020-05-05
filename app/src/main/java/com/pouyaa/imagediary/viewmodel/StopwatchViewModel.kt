package com.pouyaa.imagediary.viewmodel

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

class StopwatchViewModel : ViewModel() {

    var secondsCount = 0
        set(value) {
            field = value
            secondsPassed.postValue(value)
        }

    private var handler = Handler()
    private lateinit var runnable: Runnable

    var secondsPassed = MutableLiveData(secondsCount)

    private fun start() {

        runnable = Runnable {
            secondsCount += 1

            Timber.i("Timer is at: $secondsCount")

            handler.postDelayed(runnable, 1000)
        }

        handler.post(runnable)
    }


    private fun stop() {
        handler.removeCallbacks(runnable)
    }

    fun didClickStartButton() {
        start()
    }

    fun didClickStopButton() {
        secondsCount = 0
        stop()
    }

}