package com.l24o.workcalendar.data.rest

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.net.SocketTimeoutException

class CalendarInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain?): Response {
        try {
            val request = chain!!.request()
            return chain.proceed(request)
        } catch(e: SocketTimeoutException) {
            throw IOException("Не получилось установить связь сервером", e)
        }
    }
}