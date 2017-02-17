package com.l24o.workcalendar

import android.support.multidex.MultiDexApplication
import com.l24o.workcalendar.di.modules.AppModule
import com.l24o.workcalendar.di.AppComponent
import com.l24o.workcalendar.di.DaggerAppComponent

class CalendarApplication : MultiDexApplication() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }
}