package com.pouyaa.imagediary.utils

import android.os.Handler
import timber.log.Timber

class CustomStopwatch private constructor(
    private val delegate: TickInterface? = null, private val stopAt: Int
) {

    class Factory {
        private var stopAt = 4
        private var delegate: TickInterface? = null

        fun setStopAt(stopAt: Int): Factory {
            this.stopAt = stopAt
            return this
        }

        fun setDelegate(delegate: TickInterface?): Factory {
            this.delegate = delegate
            return this
        }

        fun build(): CustomStopwatch {
            return CustomStopwatch(delegate, stopAt)
        }


    }

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
                if (secondsCount < stopAt) {
                    secondsCount += 1

                    Timber.i("Timer is at: $secondsCount")

                    runnable?.let {
                        handler.postDelayed(it, 1000)
                    }
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