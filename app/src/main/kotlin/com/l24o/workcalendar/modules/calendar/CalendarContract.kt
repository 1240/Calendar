package com.l24o.workcalendar.modules.calendar

import com.l24o.workcalendar.common.mvp.IPresenter
import com.l24o.workcalendar.common.mvp.IView
import com.l24o.workcalendar.data.rest.models.Day
import com.l24o.workcalendar.data.rest.models.Holiday


interface ICalendarView : IView {
    fun setLoadingVisible(isVisible: Boolean)
    fun fillHolidays(holidays: List<Holiday>)
    fun fillDays(days: List<Day>)
    fun fillNorms(daysCalendar: Int,
                  daysWork: Int,
                  holidays: Int,
                  hCount40: Double,
                  hCount36: Double,
                  hCount24: Double)
}

interface ICalendarPresenter : IPresenter<ICalendarView> {
    fun currentMonthChange(monthNumber: Int)
}

