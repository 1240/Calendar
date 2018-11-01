package com.l24o.workcalendar.common.decorator

import android.content.Context
import android.graphics.Typeface
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.text.style.TypefaceSpan
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

class HighlightWeekendsDecoratorWithToday(val context: Context) : DayViewDecorator {

    override fun shouldDecorate(day: CalendarDay): Boolean {
        val weekDay = day.date.dayOfWeek
        return (weekDay == DayOfWeek.SATURDAY || weekDay == DayOfWeek.SUNDAY) && day.date.isToday()
    }

    override fun decorate(view: DayViewFacade) {
        ContextCompat.getDrawable(context, R.drawable.circle_drawable_weekend_with_today)?.let(view::setBackgroundDrawable)
        view.addSpan(ForegroundColorSpan(ContextCompat.getColor(context, R.color.colorWhite)))
        view.addTodaySpans()
    }
}

class HoliDayDecoratorWithToday(
        val context: Context,
        val days: List<Day>
) : DayViewDecorator {

    override fun decorate(view: DayViewFacade) {
        ContextCompat.getDrawable(context, R.drawable.circle_drawable_light_with_today)?.let(view::setBackgroundDrawable)
        view.addTodaySpans()
    }

    override fun shouldDecorate(day: CalendarDay): Boolean {
        val first = days.firstOrNull { it.day.isSameDay(day.date) && it.type == TypeOfDay.SHORT_DAY }
        return first != null && day.date.isToday()
    }
}
class ShortDayDecoratorWithToday(
        val context: Context,
        val days: List<Day>
) : DayViewDecorator {

    override fun decorate(view: DayViewFacade) {
        ContextCompat.getDrawable(context, R.drawable.circle_drawable_primary_with_today)?.let(view::setBackgroundDrawable)
        view.addSpan(ForegroundColorSpan(ContextCompat.getColor(context, R.color.colorWhite)))
        view.addTodaySpans()
    }

    override fun shouldDecorate(day: CalendarDay): Boolean {
        val first = days.firstOrNull { it.day.isSameDay(day.date) && it.type != TypeOfDay.SHORT_DAY }
        return first != null && day.date.isToday()
    }
}

class CurrentDayDecorator(val context: Context) : DayViewDecorator {

    override fun shouldDecorate(day: CalendarDay): Boolean {
        return day.date.isToday()
    }

    override fun decorate(view: DayViewFacade) {
        ContextCompat.getDrawable(context, R.drawable.circle_drawable_current)?.let(view::setBackgroundDrawable)
        view.addSpan(ForegroundColorSpan(ContextCompat.getColor(context, R.color.colorPrimary)))
        view.addTodaySpans()
    }
}

fun DayViewFacade.addTodaySpans() {
    addSpan(StyleSpan(Typeface.BOLD))
    addSpan(RelativeSizeSpan(1.05f))
}