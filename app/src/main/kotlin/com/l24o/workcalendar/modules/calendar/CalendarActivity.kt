package com.l24o.workcalendar.modules.calendar

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import com.l24o.workcalendar.Constants
import com.l24o.workcalendar.R
import com.l24o.workcalendar.common.CurrentDayDecorator
import com.l24o.workcalendar.common.HighlightWeekendsDecorator
import com.l24o.workcalendar.common.mvp.MvpActivity
import com.l24o.workcalendar.data.rest.models.Day
import com.l24o.workcalendar.data.rest.models.Holiday
import com.l24o.workcalendar.data.rest.models.TypeOfDay
import com.l24o.workcalendar.di.AppComponent
import com.l24o.workcalendar.extensions.isSameDay
import com.l24o.workcalendar.extensions.toString
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import kotlinx.android.synthetic.main.activity_calendar.*
import kotlinx.android.synthetic.main.item_holiday.view.*
import java.util.*
import javax.inject.Inject


class CalendarActivity : MvpActivity(), ICalendarView {

    @Inject lateinit var presenter: ICalendarPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)
        initViews()
        presenter.onViewAttached()
    }

    override fun resolveDependencies(appComponent: AppComponent) {
        DaggerCalendarComponent.builder()
                .appComponent(appComponent)
                .calendarModule(CalendarModule(this))
                .build()
                .inject(this)
    }

    override fun beforeDestroy() {
        presenter.dropView()
    }

    override fun fillHolidays(holidays: List<Holiday>) {
        holidaysWrapper.removeAllViews()
        holidays.forEach {
            val view = LayoutInflater.from(this).inflate(R.layout.item_holiday, null, false)
            view.apply {
                if (it.date.size == 1) {
                    holidayDate.text = it.date.first().toString(Constants.CALENDAR_HOLIDAY_DATE_FORMAT)
                } else {
                    holidayDate.text = "c ${it.date.first().toString(Constants.CALENDAR_HOLIDAY_DATE_FORMAT)} по ${it.date.last().toString(Constants.CALENDAR_HOLIDAY_DATE_FORMAT)}"
                }
                holidayName.text = it.title
            }
            holidaysWrapper.addView(view)
        }
    }

    override fun fillDays(days: List<Day>) {
        calendarView.addDecorators(
                object : DayViewDecorator {
                    override fun decorate(view: DayViewFacade) {
                        view.setBackgroundDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.circle_drawable_light))
                    }

                    override fun shouldDecorate(day: CalendarDay): Boolean {
                        val first = days.firstOrNull { it.day!!.isSameDay(day.date) && it.type == TypeOfDay.SHORT_DAY }
                        return first != null
                    }

                },
                object : DayViewDecorator {
                    override fun decorate(view: DayViewFacade) {
                        view.setBackgroundDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.circle_drawable_primary))
                        view.addSpan(ForegroundColorSpan(ContextCompat.getColor(applicationContext, R.color.colorWhite)))
                    }

                    override fun shouldDecorate(day: CalendarDay): Boolean {
                        val first = days.firstOrNull { it.day!!.isSameDay(day.date) && it.type != TypeOfDay.SHORT_DAY }
                        return first != null
                    }

                })
    }

    override fun setLoadingVisible(isVisible: Boolean) {

    }

    private fun initViews() {
        val startOfYear = Calendar.getInstance()
        startOfYear.set(Calendar.MONTH, Calendar.JANUARY)
        startOfYear.set(Calendar.DAY_OF_MONTH, 1)

        val endOfYear = Calendar.getInstance()
        endOfYear.set(Calendar.MONTH, Calendar.DECEMBER)
        endOfYear.set(Calendar.DAY_OF_MONTH, 31)

        calendarView.apply {
            setTileHeightDp(32)
            setOnMonthChangedListener { materialCalendarView, calendarDay ->
                presenter.currentMonthChange(calendarDay.month)
            }
            addDecorators(
                    HighlightWeekendsDecorator(applicationContext),
                    CurrentDayDecorator(applicationContext)
            )
            state().edit()
                    .setMinimumDate(startOfYear.time)
                    .setMaximumDate(endOfYear.time)
                    .commit()
            selectionMode = MaterialCalendarView.SELECTION_MODE_NONE
        }
    }
}

