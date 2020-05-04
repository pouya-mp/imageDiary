package com.pouyaa.imagediary.ui

import android.app.Application
import timber.log.Timber

class ImageDiaryApp : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}