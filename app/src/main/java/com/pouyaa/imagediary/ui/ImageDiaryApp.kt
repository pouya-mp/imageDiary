package com.pouyaa.imagediary.ui

import android.app.Application
import timber.log.Timber

class ImageDiaryApp : Application() {

    companion object {
        lateinit var instance: ImageDiaryApp
    }

    override fun onCreate() {
        super.onCreate()

        instance = this

        Timber.plant(Timber.DebugTree())
    }
}