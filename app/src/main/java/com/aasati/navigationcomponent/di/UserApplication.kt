package com.aasati.navigationcomponent.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class UserApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            // declare used Android context
            androidContext(this@UserApplication)

            // declare modules
            modules(viewModelModule, databaseModule)
        }
    }
}