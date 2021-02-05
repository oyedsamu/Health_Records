package com.decadevs.healthrecords.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.decadevs.healthrecords.repository.BaseRepository
import com.decadevs.healthrecords.repository.HealthRecordsRepositoryImpl

class ViewModelFactory(
    private val repository: BaseRepository,
    private val context: Context
) : ViewModelProvider.NewInstanceFactory() {

    /**
     * When adding a new view model class, create another instance with
     * modelClass.isAssignableFrom(NewViewModel::class.java)
     */

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HealthRecordsViewModel::class.java) ->
                HealthRecordsViewModel(repository as HealthRecordsRepositoryImpl) as T

            else -> throw IllegalArgumentException("View Model Class Not found")
        }
    }
}