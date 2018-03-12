package com.ragaisis.newsreader

import android.app.Application
import android.support.v7.app.AppCompatDelegate
import com.ragaisis.newsreader.dagger.module.ApplicationModule
import com.ragaisis.newsreader.dagger.components.ApplicationComponent
import com.ragaisis.newsreader.dagger.components.DaggerApplicationComponent
import com.ragaisis.newsreader.dagger.module.ApiModule

class MainApplication : Application() {

    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(applicationContext))
                .apiModule(ApiModule())
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        component.inject(this)
    }
}