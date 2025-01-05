package com.example.red30.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.red30.MainViewModel
import com.example.red30.data.ConferenceRepository
import com.example.red30.data.InMemoryConferenceRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    singleOf(::InMemoryConferenceRepository) bind ConferenceRepository::class
    viewModelOf(::MainViewModel)
}

val dataStoreModule = module {
    single<DataStore<Preferences>> {
        PreferenceDataStoreFactory.create(
            produceFile = {
                androidContext().preferencesDataStoreFile("favorite_ids")
            }
        )
    }
}
