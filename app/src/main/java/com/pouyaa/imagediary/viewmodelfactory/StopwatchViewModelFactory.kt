package com.pouyaa.imagediary.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pouyaa.imagediary.viewmodel.StopwatchViewModel

@Suppress("UNCHECKED_CAST")
class StopwatchViewModelFactory(private val stopAt: Int, private val app: Application) :
    ViewModelProvider.AndroidViewModelFactory(app) {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StopwatchViewModel::class.java)) {
            return StopwatchViewModel(stopAt, app) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel Type")
        }
    }
}