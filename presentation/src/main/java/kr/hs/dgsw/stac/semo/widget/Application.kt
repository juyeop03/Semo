package kr.hs.dgsw.stac.semo.widget

import android.app.Application
import kr.hs.dgsw.stac.semo.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application : Application(){

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Application)
            val modules = listOf(viewModelModule)
            modules(modules)
        }
    }
}