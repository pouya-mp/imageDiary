package com.pouyaa.imagediary.viewmodel

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

class CountdownViewModel : ViewModel() {

    companion object {
        private const val NUMBER_OF_SECONDS = 10
    }

    private var handler = Handler()
    private var runnable: Runnable? = null

    private var countDownTime = NUMBER_OF_SECONDS
        set(value) {
            field = value
            remainingTime.postValue(value)

            if (field <= 0) {
                _countDownTimerDidFinish.postValue(true)
            }
        }
    val remainingTime = MutableLiveData(NUMBER_OF_SECONDS)


    private val _countDownTimerDidFinish = MutableLiveData(false)
    val countDownTimerDidFinish: LiveData<Boolean>
        get() = _countDownTimerDidFinish

    private fun startCountdownTimer() {
        if (runnable == null) {
            runnable = Runnable {
                if (countDownTime > 0) {

                    countDownTime--
                    Timber.i("$countDownTime")
                    handler.postDelayed(runnable, 1000)

                }
            }
            handler.post(runnable)
        }


    }

    private fun stopCountdownTimer() {
        countDownTime = NUMBER_OF_SECONDS
        handler.removeCallbacks(runnable)
        runnable = null
    }

    fun didClickStartButton() {
        startCountdownTimer()
    }

    fun didClickStopButton() {
        stopCountdownTimer()
    }

    fun onCountdownTimerFinishCompleted() {
        _countDownTimerDidFinish.value = false
    }
}