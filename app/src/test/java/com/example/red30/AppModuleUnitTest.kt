package com.example.red30

import android.app.Application
import android.content.Context
import androidx.lifecycle.SavedStateHandle
import com.example.red30.di.appModule
import com.example.red30.di.dataStoreModule
import org.junit.Test
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.test.KoinTest
import org.koin.test.verify.verify

@OptIn(KoinExperimentalAPI::class)
class AppModuleUnitTest: KoinTest {

    @Test
    fun verifyAppModule() {
        appModule.verify(
            extraTypes = listOf(
                SavedStateHandle::class,
                Application::class,
                Context::class,
            )
        )
    }

    @Test
    fun verifyDataStoreModule() {
        dataStoreModule.verify(
            extraTypes = listOf(
                Application::class,
                Context::class,
            )
        )
    }
}
