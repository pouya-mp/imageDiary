package com.pouyaa.imagediary.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pouyaa.imagediary.viewmodel.CountdownViewModel
import java.lang.IllegalArgumentException

class CountdownViewModelFactory(private val remainingTimeInSeconds: Int) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CountdownViewModel::class.java)) {
            return CountdownViewModel(remainingTimeInSeconds) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Type")
    }

}
