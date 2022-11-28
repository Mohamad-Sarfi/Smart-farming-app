package com.example.smartfarming.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import javax.inject.Inject

class UserPreferenceRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
}