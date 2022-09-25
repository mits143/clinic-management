package com.clinic.management

import android.app.Application
import com.clinic.management.di.appModule
import com.clinic.management.di.repoModule
import com.clinic.management.di.viewModelModule
import com.clinic.management.util.Prefs
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

val prefs: Prefs by lazy {
    App.prefs!!
}

class App : Application() {
    companion object {
        var prefs: Prefs? = null
        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
        prefs = Prefs(applicationContext)

        startKoin {
            androidContext(this@App)
            modules(listOf(appModule, repoModule, viewModelModule))
        }
    }
}