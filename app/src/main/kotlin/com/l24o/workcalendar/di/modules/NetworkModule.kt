package com.l24o.workcalendar.di.modules

import com.l24o.workcalendar.Constants
import com.l24o.workcalendar.data.rest.CalendarInterceptor
import com.l24o.workcalendar.data.rest.DateTransformer
import com.l24o.workcalendar.data.rest.TypeOfDayTransformer
import com.l24o.workcalendar.data.rest.datasource.CalendarDataSource
import com.l24o.workcalendar.data.rest.models.TypeOfDay
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.simpleframework.xml.core.Persister
import org.simpleframework.xml.transform.RegistryMatcher
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.util.*
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
        val matcher = RegistryMatcher()
        matcher.bind(TypeOfDay::class.java, TypeOfDayTransformer())
        matcher.bind(Date::class.java, DateTransformer())
        val adapter = Retrofit.Builder()
                .baseUrl(Constants.API_MAIN_ENDPOINT_URL)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(SimpleXmlConverterFactory.create(Persister(matcher)))
                .build()

        return adapter
    }

    @Provides
    @Singleton
    fun provideAuthDataSource(@Named("default") retrofit: Retrofit): CalendarDataSource {
        return retrofit.create(CalendarDataSource::class.java)
    }
}