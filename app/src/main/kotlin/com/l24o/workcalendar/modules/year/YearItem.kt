package com.l24o.workcalendar.modules.year

import com.l24o.workcalendar.R
import com.l24o.workcalendar.common.decorator.*
import com.l24o.workcalendar.modules.GodObject
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.item_year.view.*
import org.threeten.bp.LocalDate

class YearItem(
        val currentDate: LocalDate,
        val maximumDate: LocalDate,
        val minimumDate: LocalDate,
        val onClickListener: () -> Unit
) : Item<ViewHolder>() {
    override fun getLayout() = R.layout.item_year

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.item_year_container_click.setOnClickListener { onClickListener.invoke() }
        viewHolder.itemView.item_year_text_view_title.text = viewHolder.itemView.context.resources.getStringArray(R.array.months).getOrNull(currentDate.monthValue - 1)
        with(viewHolder.itemView.item_year_calendar) {
            currentDate = CalendarDay.from(this@YearItem.currentDate)
            state().edit()
                    .setMinimumDate(this@YearItem.minimumDate)
                    .setShowWeekDays(false)
                    .isCacheCalendarPositionEnabled(true)
                    .setMaximumDate(this@YearItem.maximumDate)
                    .commit()
            topbarVisible = false

            addDecorators(
                    CurrentDayDecorator(context),
                    HighlightWeekendsDecorator(context),
                    HoliDayDecorator(context, GodObject.calendar.days),
                    ShortDayDecorator(context, GodObject.calendar.days),
                    HighlightWeekendsDecoratorWithToday(context),
                    HoliDayDecoratorWithToday(context, GodObject.calendar.days),
                    ShortDayDecoratorWithToday(context, GodObject.calendar.days)
            )
            selectionMode = MaterialCalendarView.SELECTION_MODE_NONE
        }
    }

    override fun getId() = currentDate.monthValue.toLong()

    override fun isRecyclable() = false
}