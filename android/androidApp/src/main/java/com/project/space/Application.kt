package com.project.space

import com.project.space.di.commonModule
import com.project.space.di.navigationCoordinatorsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin

class Application : android.app.Application(), KoinComponent {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Application)
            modules(commonModule, navigationCoordinatorsModule)
        }
    }
}
