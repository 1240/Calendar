package com.l24o.workcalendar.common

import android.content.Context
import android.support.v4.content.ContextCompat
import android.text.style.ForegroundColorSpan
import com.l24o.workcalendar.R
import com.l24o.workcalendar.extensions.isSameDay
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import java.util.*


/**
 * @author Alexander Popov on 16/02/2017.
 */
class HighlightWeekendsDecorator(val context: Context) : DayViewDecorator {


    private val calendar = Calendar.getInstance()

    override fun shouldDecorate(day: CalendarDay): Boolean {
        day.copyTo(calendar)
        val weekDay = calendar.get(Calendar.DAY_OF_WEEK)
        return weekDay == Calendar.SATURDAY || weekDay == Calendar.SUNDAY
    }

    override fun decorate(view: DayViewFacade) {
        view.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.circle_drawable_primary))
        view.addSpan(ForegroundColorSpan(ContextCompat.getColor(context, R.color.colorWhite)))
    }
}

class CurrentDayDecorator(val context: Context) : DayViewDecorator {

    override fun shouldDecorate(day: CalendarDay): Boolean {
        return Date().isSameDay(day.date)
    }

    override fun decorate(view: DayViewFacade) {
        view.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.circle_drawable_current))
        view.addSpan(ForegroundColorSpan(ContextCompat.getColor(context, R.color.colorWhite)))
    }
}