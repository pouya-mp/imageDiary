package com.pouyaa.imagediary.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pouyaa.imagediary.utils.CustomCountdown

private val START_BUZZ_PATTERN = longArrayOf(100, 100, 100, 100, 100, 100)
private val STOP_BUZZ_PATTERN = longArrayOf(0, 200)
private val TICK_BUZZ_PATTERN = longArrayOf(100)
private val NO_BUZZ_PATTERN = longArrayOf(0)

class CountdownViewModel(private val remainingTimeInSeconds: Int) : ViewModel() {

    enum class BuzzType(val pattern: LongArray) {
        START(START_BUZZ_PATTERN),
        STOP(STOP_BUZZ_PATTERN),
        TICK(TICK_BUZZ_PATTERN),
        NO_BUZZ(NO_BUZZ_PATTERN)
    }

    private val countDownTimer: CustomCountdown = CustomCountdown.Factory().apply {
        setStartingSeconds(remainingTimeInSeconds)
        setDelegate(object : CustomCountdown.TickInterface {
            override fun countdownTimerDidTick(second: Int) {
                _remainingTime.postValue(second)
                _eventBuzz.postValue(BuzzType.TICK)

                if (second <= 0) {
                    _countDownTimerDidFinish.value = true
                }
            }
        })
    }.build()

    private val _remainingTime = MutableLiveData(remainingTimeInSeconds)
    val remainingTime: LiveData<Int>
        get() = _remainingTime

    private val _countDownTimerDidFinish = MutableLiveData(false)
    val countDownTimerDidFinish: LiveData<Boolean>
        get() = _countDownTimerDidFinish

    private val _eventBuzz = MutableLiveData<BuzzType>()
    val eventBuzz: LiveData<BuzzType>
        get() = _eventBuzz

    fun onStartClick() {
        countDownTimer.start()
        _eventBuzz.value = BuzzType.START
    }

    fun onStopClick() {
        countDownTimer.stop()
        _eventBuzz.value = BuzzType.STOP
    }

    fun onCountdownTimerFinishCompleted() {
        _countDownTimerDidFinish.value = false
    }

    fun onBuzzComplete() {
        _eventBuzz.value = BuzzType.NO_BUZZ
    }

    override fun onCleared() {
        countDownTimer.stop()
        super.onCleared()
    }
}