package com.example.smartfarming.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

private val USER_PREFERENCE_NAME = "app_datastore"

//@InstallIn(SingletonComponent::class)
//@Module
//object DataStoreModule {
//
//    @Singleton
//    @Provides
//    fun provideUserPreferenceDataStore(
//        @ApplicationContext appContext : Context) : DataStore<Preferences> {
//        return PreferenceDataStoreFactory.create(
//            corruptionHandler = ReplaceFileCorruptionHandler(
//                produceNewData = { emptyPreferences() }
//            ),
//            migrations = listOf(SharedPreferencesMigration(appContext, USER_PREFERENCE_NAME)),
//            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
//            produceFile = {appContext.preferencesDataStoreFile(USER_PREFERENCE_NAME)}
//        )
//    }
//}