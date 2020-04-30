package com.pouyaa.imagediary.utils

import android.os.Handler
import android.util.Log
import android.widget.EditText

class CustomCountdown() {


    private var handler = Handler()
    private lateinit var runnable: Runnable


    fun startCountdownTimer(time: Int, countdownView: EditText) {
        var countDownTime = time
        runnable = Runnable {
            if (countDownTime > 0) {
                countdownView.isFocusable = false
                countdownView.isFocusableInTouchMode = false
                countDownTime--
                countdownView.setText("$countDownTime")
                Log.i("cnt", countDownTime.toString())
                handler.postDelayed(runnable, 1000)

            }
            countdownView.isFocusable = true
            countdownView.isFocusableInTouchMode = true
        }
        handler.post(runnable)


    }

    fun stopCountdownTimer(countdownView: EditText) {
        countdownView.isFocusable = true
        countdownView.isFocusableInTouchMode = true
        handler.removeCallbacks(runnable)
    }

}