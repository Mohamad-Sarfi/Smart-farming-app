package com.example.smartfarming.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferences (
    context: Context
){
    private val USER_PREFERENCE_NAME = "app_datastore"
    private val applicationContext = context.applicationContext
    private val Context.dataStore by preferencesDataStore(USER_PREFERENCE_NAME)
    private val dataStore : DataStore<Preferences> = applicationContext.dataStore

    val authToken : Flow<String?>
        get() = dataStore.data.map { preferences ->
            preferences[KEY_AUTH]
        }

    val firstTime : Flow<String?>
        get() = dataStore.data.map {
            it[FIRST_TIME]
        }

    val lastGarden : Flow<String?>
        get() = dataStore.data.map {
            it[LAST_GARDEN]
        }

    suspend fun saveAuthToken(authToken : String){
        dataStore.edit { preferences ->
            preferences[KEY_AUTH] = authToken
        }
    }

    suspend fun setFirstTime(value : String){
        dataStore.edit { preferences ->
            preferences[FIRST_TIME] = value
        }
    }

    companion object{
        private val KEY_AUTH = stringPreferencesKey("auth_key")
        private val FIRST_TIME = stringPreferencesKey("first_time")
        private val LAST_GARDEN = stringPreferencesKey("last_garden")

        private var INSTANCE : UserPreferences? = null

        fun getInstance(context: Context) : UserPreferences {
            return INSTANCE?: synchronized(this) {
                INSTANCE?.let {
                    return it
                }
                val instance = UserPreferences(context)
                INSTANCE = instance
                instance
            }
        }
    }
}

