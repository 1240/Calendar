package com.l24o.workcalendar.extensions

import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Alexander Popov on 10/02/2017.
 */

fun Date.toString(format: String): String {
    return SimpleDateFormat(format, Locale.getDefault()).format(this)
}

fun Date.isSameMouth(month: Int): Boolean {
    val calendar = Calendar.getInstance()
    calendar.time = this
    return month == calendar.get(Calendar.MONTH)
}

fun Date.isSameDay(date: Date): Boolean {
    val cal1 = Calendar.getInstance()
    val cal2 = Calendar.getInstance()
    cal1.time = this
    cal2.time = date
    return cal1.get(Calendar.DAY_OF_YEAR) === cal2.get(Calendar.DAY_OF_YEAR)
}

fun Date.monthNumber(): Int {
    val cal1 = Calendar.getInstance()
    cal1.time = this
    return cal1.get(Calendar.MONTH)
}

fun Date.startOfDay(): Date {
    val cal = Calendar.getInstance()
    cal.time = this
    cal.set(Calendar.HOUR_OF_DAY, cal.getMinimum(Calendar.HOUR_OF_DAY));
    cal.set(Calendar.MINUTE, cal.getMinimum(Calendar.MINUTE));
    cal.set(Calendar.SECOND, cal.getMinimum(Calendar.SECOND));
    cal.set(Calendar.MILLISECOND, cal.getMinimum(Calendar.MILLISECOND));
    return cal.time
}