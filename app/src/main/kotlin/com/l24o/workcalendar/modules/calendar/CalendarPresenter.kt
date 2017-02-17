package com.l24o.workcalendar.modules.calendar

import com.l24o.workcalendar.common.CalendarAssetsManager
import com.l24o.workcalendar.common.mvp.RxPresenter
import com.l24o.workcalendar.data.rest.models.Calendar
import com.l24o.workcalendar.data.rest.models.Day
import com.l24o.workcalendar.data.rest.models.Holiday
import com.l24o.workcalendar.extensions.monthNumber
import com.l24o.workcalendar.extensions.parsedMessage
import rx.lang.kotlin.plusAssign
import java.util.*
import javax.inject.Inject


class CalendarPresenter @Inject constructor(view: ICalendarView) : RxPresenter<ICalendarView>(view), ICalendarPresenter {

    // если хотим получать из БД то юзаем calendarRepository если локально то юзаем assets
//        @Inject lateinit var calendarRepository: CalendarRepository
    @Inject lateinit var calendarAssetsManager: CalendarAssetsManager

    private var calendar: Calendar? = null
    private var mapOfHolidays: HashMap<Int, List<Holiday>> = HashMap()
    private var mapOfDays: HashMap<Int, List<Day>> = HashMap()
    private var currentMonth = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH)
    override fun onViewAttached() {
        super.onViewAttached()
        view?.setLoadingVisible(true)
        subscriptions += calendarAssetsManager.calendar()
                .subscribe(
                        { response ->
                            this.calendar = response
                            fillData()
                            view?.setLoadingVisible(false)
                        },
                        { error ->
                            error.printStackTrace()
                            view?.showMessage(error.parsedMessage())
                            view?.setLoadingVisible(false)
                        }
                )
    }

    override fun currentMonthChange(monthNumber: Int) {
        currentMonth = monthNumber
        view?.fillHolidays(mapOfHolidays[currentMonth]?: ArrayList())
        val month = java.util.Calendar.getInstance()
        month.set(java.util.Calendar.MONTH, monthNumber)
        val dayOfMonth = month.getActualMaximum(java.util.Calendar.DAY_OF_MONTH)
//        view?.fillNorms(dayOfMonth, )
    }

    private fun fillData() {
        calendar!!.days!!.forEach {
            day ->
            day.holidayId?.let {
                val monthNumber = day.day!!.monthNumber()
                val holiday = calendar!!.holidays!!.first { it.id == day.holidayId }
                holiday.date.add(day.day!!)
                if (mapOfHolidays.containsKey(monthNumber)) {
                    val list = mapOfHolidays[monthNumber]!!
                    if (!list.contains(holiday)) {
                        (mapOfHolidays[monthNumber] as ArrayList).add(holiday)
                    } else {

                    }
                } else {
                    val list = ArrayList<Holiday>()
                    list.add(holiday)
                    mapOfHolidays.put(monthNumber, list)
                }
                if (mapOfDays.containsKey(monthNumber)) {
                    val list = mapOfDays[monthNumber]!!
                    if (!list.contains(day)) {
                        (mapOfDays[monthNumber] as ArrayList).add(day)
                    } else {

                    }
                } else {
                    val list = ArrayList<Day>()
                    list.add(day)
                    mapOfDays.put(monthNumber, list)
                }
            }
        }
        view?.fillDays(calendar!!.days!!)
        if (mapOfHolidays.containsKey(currentMonth)) {
            view?.fillHolidays(mapOfHolidays[currentMonth]!!)
        }
    }
}