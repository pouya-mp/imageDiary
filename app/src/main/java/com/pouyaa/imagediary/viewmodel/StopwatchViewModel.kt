package com.pouyaa.imagediary.viewmodel

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

class StopwatchViewModel : ViewModel() {

    private var secondsCount = 0
        set(value) {
            field = value
            secondsPassed.postValue(value)
        }

    private var handler = Handler()
    private var runnable: Runnable? = null

    var secondsPassed = MutableLiveData(secondsCount)

    private fun start() {
        if (runnable == null) {
            runnable = Runnable {
                secondsCount += 1

                Timber.i("Timer is at: $secondsCount")

                runnable?.let {
                    handler.postDelayed(it, 1000)
                }
            }

            runnable?.let {
                handler.post(it)
            }
        }

    }


    private fun stop() {
        runnable?.let {
            handler.removeCallbacks(it)
        }
        runnable = null
    }

    fun didClickStartButton() {
        start()
    }

    fun didClickStopButton() {
        secondsCount = 0
        stop()
    }

}