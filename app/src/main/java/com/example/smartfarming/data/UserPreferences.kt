package com.example.smartfarming.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences(
    context: Context
){
    private val applicationContext = context.applicationContext
    private val Context.dataStore by preferencesDataStore("app_datastore")
    private val dataStore : DataStore<Preferences> = applicationContext.dataStore

    val authToken : Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[KEY_AUTH]
        }

    suspend fun saveAuthToken(authToken : String){
        dataStore.edit { preferences ->
            preferences[KEY_AUTH] = authToken
        }
    }

    companion object{
        private val KEY_AUTH = stringPreferencesKey("auth_key")
    }

}

