package com.l24o.template.data.rest.datasource

import com.l24o.template.data.rest.models.Calendar
import retrofit2.http.GET
import rx.Observable


interface CalendarDataSource {

    @GET("2017/calendar.xml")
    fun calendar(): Observable<Calendar>
}