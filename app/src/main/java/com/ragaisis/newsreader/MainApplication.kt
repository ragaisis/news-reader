package com.ragaisis.newsreader

import android.app.Application
import com.ragaisis.newsreader.dagger.module.ApplicationModule
import com.ragaisis.newsreader.dagger.module.components.ApplicationComponent
import com.ragaisis.newsreader.dagger.module.components.DaggerApplicationComponent

class MainApplication : Application() {

    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(applicationContext))
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
    }
}