package com.ragaisis.newsreader.dagger.module.components

import com.ragaisis.newsreader.MainApplication
import com.ragaisis.newsreader.dagger.module.ApplicationModule
import dagger.Component

@Component(
        modules = arrayOf(
                ApplicationModule::class
        )
)
interface ApplicationComponent {

    fun inject(mainApplication: MainApplication)
}