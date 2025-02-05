package com.example.red30

import android.app.Application
import com.example.red30.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class Red30Application: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@Red30Application)
            modules(appModule)
        }
    }
}
