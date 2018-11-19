package com.l24o.workcalendar.modules.month

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.l24o.findmylove.modules.dialogs.HolidaysListDialogFragment
import com.l24o.workcalendar.R
import com.l24o.workcalendar.common.decorator.*
import com.l24o.workcalendar.data.rest.models.Holiday
import com.l24o.workcalendar.modules.GodObject
import com.l24o.workcalendar.modules.calendar.ui_model.Norm
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.xwray.groupie.Group
import kotlinx.android.synthetic.main.fragment_month.*
import org.threeten.bp.LocalDate

class MonthFragment : Fragment() {

    private lateinit var viewModel: MonthViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_month, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(MonthViewModel::class.java)
        initViews()
        subscribeEvents()
    }

    private fun subscribeEvents() {
        viewModel.fillHolidaysEvent.observe(this, androidx.lifecycle.Observer(this::fillHolidays))
        viewModel.fillNormsEvent.observe(this, androidx.lifecycle.Observer(this::fillNorms))
        viewModel.showHolidaysEvent.observe(this, androidx.lifecycle.Observer(this::showHolidays))
    }

    private fun fillHolidays(holidays: List<Holiday>) {
        if (holidays.isEmpty()) {
            activity_calendar_image_arrow.visibility = View.GONE
            activity_calendar_image_holidays.setImageResource(R.drawable.ic_frankenstein)
            activity_calendar_text_view_holidays.setText(R.string.no_holidays)
        } else {
            activity_calendar_image_arrow.visibility = View.VISIBLE
            activity_calendar_image_holidays.setImageResource(R.drawable.ic_dance)
            activity_calendar_text_view_holidays.setText(R.string.holidays)
        }
    }

    private fun fillNorms(norm: Norm) {
        with(norm) {
            normsCountCalendar.text = daysCalendar.toString()
            normsCountWork.text = daysWork.toString()
            normsCountHoliday.text = holidays.toString()
            hoursCount40.text = hCount40.toString().format("%.2f")
            hoursCount36.text = hCount36.toString().format("%.2f")
            hoursCount24.text = hCount24.toString().format("%.2f")
        }
    }

    private fun showHolidays(items: List<Group>) {
        with(HolidaysListDialogFragment.newInstance()) {
            setItems(items)
            show(this@MonthFragment.fragmentManager, HolidaysListDialogFragment.TAG)
        }
    }

    private fun initViews() {
        val startOfYear = LocalDate.ofYearDay(GodObject.minYear, 1)
        val endOfYear = LocalDate.ofYearDay(GodObject.maxYear, LocalDate.now().withYear(GodObject.maxYear).lengthOfYear())

        calendarView.apply {
            setTileHeightDp(32)
            setTitleMonths(R.array.months)
            titleAnimationOrientation = 1
            setOnMonthChangedListener { _, calendarDay ->
                viewModel.currentMonthChange(calendarDay.month)
            }
            addDecorators(
                    CurrentDayDecorator(context),
                    HighlightWeekendsDecorator(context),
                    HoliDayDecorator(context, GodObject.days),
                    ShortDayDecorator(context, GodObject.days),
                    HighlightWeekendsDecoratorWithToday(context),
                    HoliDayDecoratorWithToday(context, GodObject.days),
                    ShortDayDecoratorWithToday(context, GodObject.days)
            )
            state().edit()
                    .setMinimumDate(startOfYear)
                    .setMaximumDate(endOfYear)
                    .commit()
            selectionMode = MaterialCalendarView.SELECTION_MODE_NONE
        }

        activity_calendar_container_holidays.setOnClickListener {
            viewModel.onHolidaysClick()
        }
    }

    fun scrollTo(localDate: LocalDate) {
        calendarView?.currentDate = CalendarDay.from(localDate)
    }

    fun scrollToToday() {
        calendarView?.currentDate = CalendarDay.from(LocalDate.now())
    }

}