package com.l24o.template

import android.app.Application
import com.l24o.template.di.modules.AppModule
import com.l24o.stels.di.AppComponent
import com.l24o.stels.di.DaggerAppComponent

class CalendarApplication : Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }
}