package com.l24o.workcalendar.modules

import com.l24o.workcalendar.data.rest.models.Calendar
import com.l24o.workcalendar.data.rest.models.Holiday
import com.l24o.workcalendar.data.rest.models.TypeOfDay
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.Month
import org.threeten.bp.Year
import java.util.*

object GodObject {

    val mapOfHolidays: HashMap<Int, MutableList<Holiday>> = HashMap()
    val mapOfHolidates: HashMap<Int, HashSet<LocalDate>> = HashMap()
    val mapOfShortDates: HashMap<Int, HashSet<LocalDate>> = HashMap()

    lateinit var calendar: Calendar

    fun set(calendar: Calendar) {
        this.calendar = calendar
        fillData()
    }

    private fun fillData() {
        calendar.days.forEach { day ->
            day.holidayId?.takeIf { day.type == TypeOfDay.REST_DAY }?.let { holidayId ->
                val monthNumber = day.day.monthValue
                val holiday = calendar.holidays.first { it.id == holidayId }
                holiday.date.add(day.day)
                if (mapOfHolidays.containsKey(monthNumber)) {
                    mapOfHolidays[monthNumber]?.let { list ->
                        if (!list.contains(holiday)) {
                            mapOfHolidays[monthNumber]?.add(holiday)
                        }
                    }
                } else {
                    val list = ArrayList<Holiday>()
                    list.add(holiday)
                    mapOfHolidays.put(monthNumber, list)
                }
            }
            if (day.type == TypeOfDay.REST_DAY) {
                val monthNumber = day.day.monthValue
                if (mapOfHolidates.containsKey(monthNumber)) {
                    mapOfHolidates[monthNumber]?.let { set ->
                        if (!set.contains(day.day)) {
                            set.add(day.day)
                        }
                    }
                } else {
                    val set = HashSet<LocalDate>()
                    set.add(day.day)
                    mapOfHolidates[monthNumber] = set
                }
            }
            if (day.type == TypeOfDay.SHORT_DAY) {
                val monthNumber = day.day.monthValue
                if (mapOfShortDates.containsKey(monthNumber)) {
                    mapOfShortDates[monthNumber]?.let { set ->
                        if (!set.contains(day.day)) {
                            set.add(day.day)
                        }
                    }
                } else {
                    val set = HashSet<LocalDate>()
                    set.add(day.day)
                    mapOfShortDates[monthNumber] = set
                }
            }
        }
        for (dayOfYear in 1..LocalDate.now().lengthOfYear()) {
            val time = LocalDate.now().withDayOfYear(dayOfYear).atStartOfDay().toLocalDate()
            val dayOfWeek = time.dayOfWeek
            if (dayOfWeek == DayOfWeek.SUNDAY || dayOfWeek == DayOfWeek.SATURDAY) {
                val monthNumber = time.monthValue
                if (mapOfShortDates[monthNumber] == null || mapOfShortDates[monthNumber]?.contains(time) == false) {
                    if (mapOfHolidates.containsKey(monthNumber)) {
                        mapOfHolidates[monthNumber]?.let { set ->
                            if (!set.contains(time)) {
                                set.add(time)
                            }
                        }
                    } else {
                        val set = HashSet<LocalDate>()
                        set.add(time)
                        mapOfHolidates[monthNumber] = set
                    }
                }
            }
        }
    }
}

data class YearWithMonth(
        val year: Year,
        val month: Month
)