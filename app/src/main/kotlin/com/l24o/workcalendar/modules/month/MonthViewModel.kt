package com.l24o.workcalendar.modules.month

import androidx.lifecycle.MutableLiveData
import com.l24o.findmylove.base.BaseViewModel
import com.l24o.findmylove.modules.dialogs.HolidayViewHolder
import com.l24o.workcalendar.Constants
import com.l24o.workcalendar.data.rest.models.Holiday
import com.l24o.workcalendar.modules.GodObject
import com.l24o.workcalendar.modules.YearWithMonth
import com.l24o.workcalendar.modules.calendar.ui_model.Norm
import com.xwray.groupie.Group
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import java.util.*

class MonthViewModel : BaseViewModel() {

    val fillHolidaysEvent = MutableLiveData<List<Holiday>>()
    val fillNormsEvent = MutableLiveData<Norm>()
    val showHolidaysEvent = MutableLiveData<List<Group>>()

    private var currentYearWithMonth = YearWithMonth(
            year = LocalDate.now().year,
            month = LocalDate.now().monthValue
    )

    init {
        refreshNorms()
    }

    fun currentMonthChange(monthNumber: Int) {
        currentYearWithMonth.month = monthNumber
        refreshNorms()
    }

    fun onHolidaysClick() {
        GodObject.mapOfHolidays[currentYearWithMonth]?.map {
            HolidayViewHolder(
                    title = it.title,
                    date = if (it.date.size == 1) {
                        it.date.first().format(DateTimeFormatter.ofPattern(Constants.CALENDAR_HOLIDAY_DATE_FORMAT, Locale.getDefault()))
                    } else {
                        "c ${it.date.first().format(DateTimeFormatter.ofPattern(Constants.CALENDAR_HOLIDAY_DATE_FORMAT, Locale.getDefault()))} по ${it.date.last().format(DateTimeFormatter.ofPattern(Constants.CALENDAR_HOLIDAY_DATE_FORMAT, Locale.getDefault()))}"
                    }
            )
        }?.let {
            showHolidaysEvent.value = it
        }

    }

    private fun refreshNorms() {
        fillHolidaysEvent.value = GodObject.mapOfHolidays[currentYearWithMonth] ?: ArrayList()
        val date = LocalDate.now().withYear(currentYearWithMonth.year).withMonth(currentYearWithMonth.month)
        val dayOfMonth = date.lengthOfMonth()
        GodObject.mapOfHolidates[currentYearWithMonth]?.let {
            val workDaysCount = dayOfMonth - it.size
            fillNormsEvent.value = Norm(dayOfMonth, workDaysCount, it.size,
                    workDaysCount * 40.0 / 5 - (GodObject.mapOfShortDates[currentYearWithMonth]?.size
                            ?: 0),
                    workDaysCount * 36.0 / 5 - (GodObject.mapOfShortDates[currentYearWithMonth]?.size
                            ?: 0),
                    workDaysCount * 24.0 / 5 - (GodObject.mapOfShortDates[currentYearWithMonth]?.size
                            ?: 0)
            )
        }
    }

}