package com.l24o.workcalendar.modules.calendar

import androidx.lifecycle.MutableLiveData
import com.l24o.findmylove.base.BaseViewModel
import org.threeten.bp.LocalDate
import org.threeten.bp.format.TextStyle
import java.util.*

class CalendarViewModel : BaseViewModel() {

    val currentDay = MutableLiveData<Pair<String, String>>()

    init {
        val now = LocalDate.now()
        currentDay.value = now.dayOfMonth.toString() to now.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
    }

}