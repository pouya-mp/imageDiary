package com.pouyaa.imagediary.utils

import android.os.Handler
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import timber.log.Timber

class CustomTimer(lifecycle: Lifecycle) : LifecycleObserver {

    companion object {
        private const val TAG = "CustomTimer"
    }

    init {
        lifecycle.addObserver(this)
    }

    // The number of seconds passed after timer was started.
    var secondsCount = 0
        private set

    private var handler = Handler()
    private lateinit var runnable: Runnable

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun start() {

        runnable = Runnable {
            secondsCount += 1

            Timber.i("Timer is at: $secondsCount")

            handler.postDelayed(runnable, 1000)
        }

        handler.post(runnable)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun stop() {
        handler.removeCallbacks(runnable)
    }

}