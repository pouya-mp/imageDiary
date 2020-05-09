package com.pouyaa.imagediary.utils

import android.os.Handler

class CustomCountdown private constructor(
    private val startingSeconds: Int,
    private val delegate: TickInterface? = null
) {

    class Factory {

        private var startingSeconds: Int = 0
        private var delegate: TickInterface? = null

        fun setStartingSeconds(startingSeconds: Int): Factory {
            this.startingSeconds = startingSeconds
            return this
        }

        fun setDelegate(delegate: TickInterface): Factory {
            this.delegate = delegate
            return this
        }

        fun build(): CustomCountdown {
            return CustomCountdown(startingSeconds, delegate)
        }
    }


    interface TickInterface {
        fun countdownTimerDidTick(second: Int)
    }

    private var handler = Handler()
    private var runnable: Runnable? = null
    private var countDownTime = startingSeconds
        set(value) {
            field = value
            delegate?.countdownTimerDidTick(field)
        }


    fun start() {
        if (runnable == null) {
            runnable = Runnable {
                if (countDownTime > 0) {
                    countDownTime--

                    runnable?.let {
                        handler.postDelayed(it, 1000)
                    }

                }
            }
        }

        runnable?.let {
            handler.post(it)
        }

    }

    fun stop() {
        countDownTime = startingSeconds
        runnable?.let {
            handler.removeCallbacks(it)
        }
        runnable = null
    }

}