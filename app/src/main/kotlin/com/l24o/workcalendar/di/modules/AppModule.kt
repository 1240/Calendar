package com.l24o.workcalendar.di.modules

import android.app.Application
import android.content.Context
import com.l24o.workcalendar.CalendarApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val application: CalendarApplication) {

    @Provides
    fun provideApplication(): Application {
        return application
    }

    @Provides
    @Singleton
    fun provideContext(): Context {
        return application.applicationContext
    }

}