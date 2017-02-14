package com.l24o.template.di.modules

import com.l24o.template.Constants
import com.l24o.template.data.rest.CalendarInterceptor
import com.l24o.template.data.rest.datasource.CalendarDataSource
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideDefaultHTTPClient(templateInterceptor: CalendarInterceptor): OkHttpClient {
        val client = OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                .addInterceptor(templateInterceptor)
                .build()

        return client
    }

    @Provides
    @Singleton
    fun provideTemplateInterceptor(): CalendarInterceptor {
        return CalendarInterceptor()
    }

    @Provides
    @Singleton
    @Named("default")
    fun provideDefaultRetrofitAdapter(client: OkHttpClient): Retrofit {
        val adapter = Retrofit.Builder()
                .baseUrl(Constants.API_MAIN_ENDPOINT_URL)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build()

        return adapter
    }

    @Provides
    @Singleton
    fun provideAuthDataSource(@Named("default") retrofit: Retrofit): CalendarDataSource {
        return retrofit.create(CalendarDataSource::class.java)
    }
}