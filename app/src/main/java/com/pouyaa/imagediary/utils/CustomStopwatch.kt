package com.pouyaa.imagediary.utils

import android.os.Handler
import timber.log.Timber

class CustomStopwatch(private val delegate: TickInterface? = null) {

    interface TickInterface {
        fun stopwatchDidTick(seconds: Int)
    }

    private var secondsCount = 0
        set(value) {
            field = value
            delegate?.stopwatchDidTick(field)
        }

    private var handler = Handler()
    private var runnable: Runnable? = null


    fun start() {
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


    fun stop() {
        runnable?.let {
            handler.removeCallbacks(it)
        }
        secondsCount = 0
        runnable = null
    }

}