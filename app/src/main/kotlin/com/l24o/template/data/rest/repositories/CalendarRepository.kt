package com.l24o.template.data.rest.repositories

import com.l24o.template.data.rest.datasource.CalendarDataSource
import com.l24o.template.data.rest.models.Calendar
import rx.Observable
import javax.inject.Inject


class CalendarRepository @Inject constructor(private val calendarDataSource: CalendarDataSource) : Repository() {

    fun calendar(): Observable<Calendar> {
        return calendarDataSource.calendar().compose(applySchedulers<Calendar>())
    }
}