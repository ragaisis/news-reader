package com.ragaisis.newsreader.dagger.components

import com.ragaisis.newsreader.MainApplication
import com.ragaisis.newsreader.activities.MainActivity
import com.ragaisis.newsreader.dagger.module.ApiModule
import com.ragaisis.newsreader.dagger.module.ApplicationModule
import dagger.Component

@Component(
        modules = arrayOf(
                ApplicationModule::class,
                ApiModule::class
        )
)
interface ApplicationComponent {

    fun inject(mainApplication: MainApplication)
    fun inject(mainApplication: MainActivity)
}