package com.pouyaa.imagediary.viewmodel

import android.app.Application
import android.text.format.DateUtils
import androidx.lifecycle.*
import com.pouyaa.imagediary.R
import com.pouyaa.imagediary.utils.CustomStopwatch

class StopwatchViewModel(private val stopAt: Int, private val app: Application) :
    AndroidViewModel(app) {

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

    private val _toastMessage = MutableLiveData("")
    val toastMessage: LiveData<String>
        get() = _toastMessage

    init {
        stopWatch.start()

        _toastMessage.value = app.getString(R.string.stopwatch_start_alert)
    }

    private val _shouldPopView = MutableLiveData(false)
    val shouldPopView: LiveData<Boolean>
        get() = _shouldPopView

    private val _secondsPassed = MutableLiveData(STARTING_TIME)
    val secondsPassed: LiveData<Int>
        get() = _secondsPassed

    val formattedSeconds = Transformations.map(secondsPassed) {
        DateUtils.formatElapsedTime(it.toLong())
    }


    fun didClickStopButton() {
        stopWatch.stop()
        _shouldPopView.value = true
    }

    fun didFinishShowingToast() {
        _toastMessage.value = ""
    }

}