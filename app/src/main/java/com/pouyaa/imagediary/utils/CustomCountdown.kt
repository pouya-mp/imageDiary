package com.pouyaa.imagediary.utils

import android.os.Handler
import timber.log.Timber

class CustomCountdown {

    private var handler = Handler()
    private lateinit var runnable: Runnable
    var countDownTime = 10
        private set

    fun startCountdownTimer() {

        runnable = Runnable {
            if (countDownTime > 0) {

                countDownTime--
                Timber.i("$countDownTime")
                handler.postDelayed(runnable, 1000)

            }
        }
        handler.post(runnable)

    }

    fun stopCountdownTimer() {
        countDownTime = 10
        handler.removeCallbacks(runnable)
    }

}