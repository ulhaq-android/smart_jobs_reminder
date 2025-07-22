package com.example.smartjobreminder.domain

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.GlobalContext.startKoin

class SmartJobApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@SmartJobApp)
            workManagerFactory()
            modules(appModule)
        }
    }
}