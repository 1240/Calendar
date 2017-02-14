package com.l24o.stels.di

import com.google.gson.Gson
import com.l24o.template.data.rest.AuthProvider
import com.l24o.template.data.rest.datasource.CalendarDataSource
import com.l24o.template.data.rest.datasource.ConductorDataSource
import com.l24o.template.data.rest.datasource.RegistrarDataSource
import com.l24o.template.di.modules.AppModule
import com.l24o.template.di.modules.NetworkModule
import dagger.Component
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, NetworkModule::class))
interface AppComponent {
    fun proivdeGson(): Gson
    fun provideHttpClient(): OkHttpClient
    fun provideAuthProvider(): AuthProvider
    fun provideAuthDataSource(): CalendarDataSource
    fun provideRegistrarDataSource(): RegistrarDataSource
    fun provideConductorDataSource(): ConductorDataSource
}