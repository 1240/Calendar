package com.l24o.workcalendar.modules

import com.l24o.workcalendar.data.rest.models.Calendar
import com.l24o.workcalendar.data.rest.models.Day
import com.l24o.workcalendar.data.rest.models.Holiday
import com.l24o.workcalendar.data.rest.models.TypeOfDay
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import java.io.Serializable
import java.util.*

object GodObject {

    val mapOfHolidays: HashMap<YearWithMonth, MutableList<Holiday>> = HashMap()
    val mapOfHolidates: HashMap<YearWithMonth, HashSet<LocalDate>> = HashMap()
    val mapOfShortDates: HashMap<YearWithMonth, HashSet<LocalDate>> = HashMap()
    val days = mutableListOf<Day>()
    var minYear: Int = LocalDate.now().year
    var maxYear: Int = LocalDate.now().year

    fun set(calendar: Calendar) {
        val year = calendar.year!!.toInt()
        if (year > maxYear) {
            maxYear = year
        }
        if (year < minYear) {
            minYear = year
        }
        days.addAll(calendar.days)
        fillData(calendar, year)
    }

    private fun fillData(calendar: Calendar, year: Int) {
        calendar.days.forEach { day ->
            val yearWithMonth = YearWithMonth(
                    year = year,
                    month = day.day.monthValue
            )
            day.holidayId?.takeIf { day.type == TypeOfDay.REST_DAY }?.let { holidayId ->
                val holiday = calendar.holidays.first { it.id == holidayId }
                holiday.date.add(day.day)
                if (mapOfHolidays.containsKey(yearWithMonth)) {
                    mapOfHolidays[yearWithMonth]?.let { list ->
                        if (!list.contains(holiday)) {
                            mapOfHolidays[yearWithMonth]?.add(holiday)
                        }
                    }
                } else {
                    val list = ArrayList<Holiday>()
                    list.add(holiday)
                    mapOfHolidays.put(yearWithMonth, list)
                }
            }
            if (day.type == TypeOfDay.REST_DAY) {
                if (mapOfHolidates.containsKey(yearWithMonth)) {
                    mapOfHolidates[yearWithMonth]?.let { set ->
                        if (!set.contains(day.day)) {
                            set.add(day.day)
                        }
                    }
                } else {
                    val set = HashSet<LocalDate>()
                    set.add(day.day)
                    mapOfHolidates[yearWithMonth] = set
                }
            }
            if (day.type == TypeOfDay.SHORT_DAY) {
                if (mapOfShortDates.containsKey(yearWithMonth)) {
                    mapOfShortDates[yearWithMonth]?.let { set ->
                        if (!set.contains(day.day)) {
                            set.add(day.day)
                        }
                    }
                } else {
                    val set = HashSet<LocalDate>()
                    set.add(day.day)
                    mapOfShortDates[yearWithMonth] = set
                }
            }
        }
        val withYear = LocalDate.now().withYear(year)
        for (dayOfYear in 1..withYear.lengthOfYear()) {
            val time = withYear.withDayOfYear(dayOfYear).atStartOfDay().toLocalDate()
            val yearWithMonth = YearWithMonth(
                    year = year,
                    month = time.monthValue
            )
            val dayOfWeek = time.dayOfWeek
            if (dayOfWeek == DayOfWeek.SUNDAY || dayOfWeek == DayOfWeek.SATURDAY) {
                if (mapOfShortDates[yearWithMonth] == null || mapOfShortDates[yearWithMonth]?.contains(time) == false) {
                    if (mapOfHolidates.containsKey(yearWithMonth)) {
                        mapOfHolidates[yearWithMonth]?.let { set ->
                            if (!set.contains(time)) {
                                set.add(time)
                            }
                        }
                    } else {
                        val set = HashSet<LocalDate>()
                        set.add(time)
                        mapOfHolidates[yearWithMonth] = set
                    }
                }
            }
        }
    }
}

data class YearWithMonth(
        var year: Int,
        var month: Int
) : Serializable