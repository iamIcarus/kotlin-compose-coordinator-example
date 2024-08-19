package com.example.coordinators

import android.app.Application
import com.example.coordinators.di.AppDIModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CoordinatorsApp: Application() {


    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@CoordinatorsApp)
            modules(AppDIModule)
        }
    }

    companion object {
        val TAG = CoordinatorsApp::class.java.simpleName
    }
}