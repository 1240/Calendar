package com.l24o.workcalendar.modules.calendar

import com.l24o.workcalendar.common.CalendarAssetsManager
import com.l24o.workcalendar.common.mvp.RxPresenter
import com.l24o.workcalendar.data.rest.models.Calendar
import com.l24o.workcalendar.data.rest.models.Holiday
import com.l24o.workcalendar.data.rest.models.TypeOfDay
import com.l24o.workcalendar.extensions.monthNumber
import com.l24o.workcalendar.extensions.parsedMessage
import com.l24o.workcalendar.extensions.startOfDay
import rx.lang.kotlin.plusAssign
import java.util.*
import javax.inject.Inject


class CalendarPresenter @Inject constructor(view: ICalendarView) : RxPresenter<ICalendarView>(view), ICalendarPresenter {

    // если хотим получать из БД то юзаем calendarRepository если локально то юзаем assets
//        @Inject lateinit var calendarRepository: CalendarRepository
    @Inject lateinit var calendarAssetsManager: CalendarAssetsManager

    private var calendar: Calendar? = null
    private var mapOfHolidays: HashMap<Int, List<Holiday>> = HashMap()
    private var mapOfHolyDates: HashMap<Int, HashSet<Date>> = HashMap()
    private var mapOfShortDates: HashMap<Int, HashSet<Date>> = HashMap()
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

        refreshNorms()
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
            }
            if (day.type == TypeOfDay.REST_DAY) {
                val monthNumber = day.day!!.monthNumber()
                if (mapOfHolyDates.containsKey(monthNumber)) {
                    val set = mapOfHolyDates[monthNumber]!!
                    if (!set.contains(day.day!!.startOfDay())) {
                        set.add(day.day!!.startOfDay())
                    } else {

                    }
                } else {
                    val set = HashSet<Date>()
                    set.add(day.day!!.startOfDay())
                    mapOfHolyDates.put(monthNumber, set)
                }
            }
            if (day.type == TypeOfDay.SHORT_DAY) {
                val monthNumber = day.day!!.monthNumber()
                if (mapOfShortDates.containsKey(monthNumber)) {
                    val set = mapOfShortDates[monthNumber]!!
                    if (!set.contains(day.day!!.startOfDay())) {
                        set.add(day.day!!.startOfDay())
                    } else {

                    }
                } else {
                    val set = HashSet<Date>()
                    set.add(day.day!!.startOfDay())
                    mapOfShortDates.put(monthNumber, set)
                }
            }
        }
        for (i in 0..java.util.Calendar.getInstance().getActualMaximum(java.util.Calendar.DAY_OF_YEAR)) {
            val cal = java.util.Calendar.getInstance()
            cal.set(java.util.Calendar.DAY_OF_YEAR, i)
            cal.time = cal.time.startOfDay()
            val dayOfWeek = cal.get(java.util.Calendar.DAY_OF_WEEK)
            if (dayOfWeek == java.util.Calendar.SUNDAY || dayOfWeek == java.util.Calendar.SATURDAY) {
                val monthNumber = cal.get(java.util.Calendar.MONTH)
                if (mapOfHolyDates.containsKey(monthNumber)) {
                    val set = mapOfHolyDates[monthNumber]!!
                    if (!set.contains(cal.time)) {
                        set.add(cal.time)
                    } else {

                    }
                } else {
                    val set = HashSet<Date>()
                    set.add(cal.time)
                    mapOfHolyDates.put(monthNumber, set)
                }
            }
        }
        view?.fillDays(calendar!!.days!!)
        refreshNorms()
    }

    private fun refreshNorms() {
        view?.fillHolidays(mapOfHolidays[currentMonth] ?: ArrayList())
        val month = java.util.Calendar.getInstance()
        month.set(java.util.Calendar.MONTH, currentMonth)
        val dayOfMonth = month.getActualMaximum(java.util.Calendar.DAY_OF_MONTH)
        mapOfHolyDates[currentMonth]?.let {
            val workDaysCount = dayOfMonth - it.size
            view?.fillNorms(dayOfMonth, workDaysCount, it.size,
                    workDaysCount * 40.0 / 5 - (mapOfShortDates[currentMonth]?.size ?: 0),
                    workDaysCount * 36.0 / 5 - (mapOfShortDates[currentMonth]?.size ?: 0),
                    workDaysCount * 24.0 / 5 - (mapOfShortDates[currentMonth]?.size ?: 0)
            )
        }
    }
}