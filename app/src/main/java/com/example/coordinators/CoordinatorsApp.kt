package com.example.coordinators

import android.app.Application
import com.example.coordinators.di.AppDIModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class CoordinatorsApp: Application() {


    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@CoordinatorsApp)
            androidLogger(Level.DEBUG) // Enable debug logging for Koin
            modules(AppDIModule)
        }
    }

    companion object {
        val TAG = CoordinatorsApp::class.java.simpleName
    }
}