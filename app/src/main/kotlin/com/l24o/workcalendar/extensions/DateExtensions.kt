package com.l24o.workcalendar.extensions

import org.threeten.bp.LocalDate
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.util.*

/**
 * @author Alexander Popov on 10/02/2017.
 */

fun Date.toString(format: String): String {
    return SimpleDateFormat(format, Locale.getDefault()).format(this)
}

fun LocalDate.isSameDay(date: LocalDate): Boolean {
    return this.dayOfYear == date.dayOfYear
}

fun LocalDate.isToday(): Boolean {
    return this.dayOfYear == LocalDate.now().dayOfYear
}
