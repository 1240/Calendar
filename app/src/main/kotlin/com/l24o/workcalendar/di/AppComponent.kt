package com.l24o.workcalendar.di

import android.content.Context
import com.l24o.workcalendar.data.rest.datasource.CalendarDataSource
import com.l24o.workcalendar.di.modules.AppModule
import com.l24o.workcalendar.di.modules.NetworkModule
import dagger.Component
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, NetworkModule::class))
interface AppComponent {
    fun provideHttpClient(): OkHttpClient
    fun provideAuthDataSource(): CalendarDataSource
    fun provideContext(): Context
}