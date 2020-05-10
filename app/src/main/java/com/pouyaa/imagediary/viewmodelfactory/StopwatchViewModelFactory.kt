package com.pouyaa.imagediary.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pouyaa.imagediary.viewmodel.StopwatchViewModel

@Suppress("UNCHECKED_CAST")
class StopwatchViewModelFactory(private val stopAt: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StopwatchViewModel::class.java)) {
            return StopwatchViewModel(stopAt) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel Type")
        }
    }
}