package com.pouyaa.imagediary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pouyaa.imagediary.utils.CustomCountdown

class CountdownViewModel : ViewModel() {

    companion object {
        private const val STARING_TIME = 10
    }

    private val countDownTimer: CustomCountdown = CustomCountdown.Factory().apply {
        setStartingSeconds(10)
        setDelegate(object : CustomCountdown.TickInterface {
            override fun countdownTimerDidTick(second: Int) {
                _remainingTime.postValue(second)

                if (second <= 0) {
                    _countDownTimerDidFinish.value = true
                }
            }
        })
    }.build()

    private val _remainingTime = MutableLiveData(STARING_TIME)
    val remainingTime: LiveData<Int>
        get() = _remainingTime

    private val _countDownTimerDidFinish = MutableLiveData(false)
    val countDownTimerDidFinish: LiveData<Boolean>
        get() = _countDownTimerDidFinish

    fun didClickStartButton() {
        countDownTimer.start()
    }

    fun didClickStopButton() {
        countDownTimer.stop()
    }

    fun onCountdownTimerFinishCompleted() {
        _countDownTimerDidFinish.value = false
    }

    override fun onCleared() {
        countDownTimer.stop()
        super.onCleared()
    }
}