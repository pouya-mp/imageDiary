package com.pouyaa.imagediary.viewmodel

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

class CountdownViewModel : ViewModel() {

    companion object {
        private const val NUMBER_OF_SECONDS = 100
    }

    private var handler = Handler()
    private lateinit var runnable: Runnable

    private var countDownTime = NUMBER_OF_SECONDS
        set(value) {
            field = value
            remainingTime.postValue(value)
        }
    val remainingTime = MutableLiveData(NUMBER_OF_SECONDS)

    private fun startCountdownTimer() {

        runnable = Runnable {
            if (countDownTime > 0) {

                countDownTime--
                Timber.i("$countDownTime")
                handler.postDelayed(runnable, 1000)

            }
        }
        handler.post(runnable)

    }

    private fun stopCountdownTimer() {
        countDownTime = NUMBER_OF_SECONDS
        handler.removeCallbacks(runnable)
    }

    fun didClickStartButton() {
        startCountdownTimer()
    }

    fun didClickStopButton() {
        stopCountdownTimer()
    }

}