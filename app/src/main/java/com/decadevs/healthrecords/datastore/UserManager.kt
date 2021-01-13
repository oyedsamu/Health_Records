package com.decadevs.healthrecords.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserManager(context: Context) {
    private val dataStore = context.createDataStore("user_prefs")
    private val rmDataStore = context.createDataStore("rm_prefs")

    companion object {
        val USER_UID = preferencesKey<String>("USER_ID")
        val USER_PASSWORD = preferencesKey<String>("USER_PASSWORD")

    }

    /**
     * Save user login data if remember me button is clicked
     */

    suspend fun createRememberMeSession(uid: String, password: String) {
        rmDataStore.edit {
            it[USER_PASSWORD] = password
            it[USER_UID] = uid
        }
    }



    /**
     * Get Remember me sessions
     */
    val rmUserPasswordFlow: Flow<String> = rmDataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map {
        it[USER_PASSWORD] ?: ""
    }


    val rmUserIdFlow: Flow<String> = rmDataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map {
        it[USER_UID] ?: ""
    }

}