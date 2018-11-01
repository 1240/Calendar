package com.l24o.workcalendar.common.decorator

import android.content.Context
import android.graphics.Typeface
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import androidx.core.content.ContextCompat
import com.l24o.workcalendar.R
import com.l24o.workcalendar.data.rest.models.Day
import com.l24o.workcalendar.data.rest.models.TypeOfDay
import com.l24o.workcalendar.extensions.isSameDay
import com.l24o.workcalendar.extensions.isToday
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import org.threeten.bp.DayOfWeek

class HighlightWeekendsDecorator(val context: Context) : DayViewDecorator {

    override fun shouldDecorate(day: CalendarDay): Boolean {
        val weekDay = day.date.dayOfWeek
        return weekDay == DayOfWeek.SATURDAY || weekDay == DayOfWeek.SUNDAY
    }

    override fun decorate(view: DayViewFacade) {
        ContextCompat.getDrawable(context, R.drawable.circle_drawable_weekend)?.let(view::setBackgroundDrawable)
        view.addSpan(ForegroundColorSpan(ContextCompat.getColor(context, R.color.colorWhite)))
    }
}

class HoliDayDecorator(
        val context: Context,
        val days: List<Day>
) : DayViewDecorator {

    override fun decorate(view: DayViewFacade) {
        ContextCompat.getDrawable(context, R.drawable.circle_drawable_light)?.let(view::setBackgroundDrawable)
    }

    override fun shouldDecorate(day: CalendarDay): Boolean {
        val first = days.firstOrNull { it.day.isSameDay(day.date) && it.type == TypeOfDay.SHORT_DAY }
        return first != null
    }
}

class ShortDayDecorator(
        val context: Context,
        val days: List<Day>
) : DayViewDecorator {

    override fun decorate(view: DayViewFacade) {
        ContextCompat.getDrawable(context, R.drawable.circle_drawable_primary)?.let(view::setBackgroundDrawable)
        view.addSpan(ForegroundColorSpan(ContextCompat.getColor(context, R.color.colorWhite)))
    }

    override fun shouldDecorate(day: CalendarDay): Boolean {
        val first = days.firstOrNull { it.day.isSameDay(day.date) && it.type != TypeOfDay.SHORT_DAY }
        return first != null
    }
}