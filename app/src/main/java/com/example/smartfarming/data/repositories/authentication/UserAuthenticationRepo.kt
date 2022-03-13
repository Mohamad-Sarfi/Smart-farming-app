package com.example.smartfarming.data.repositories.authentication

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

class UserAuthenticationRepo(
    private val userAuthenticationStore: DataStore<Preferences>,
    context: Context
) {
}