package com.l24o.findmylove.modules.dialogs

import com.l24o.workcalendar.R
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.item_holiday.view.*

class HolidayViewHolder(
        val title: String,
        val date: String
) : Item<ViewHolder>() {
    override fun getLayout() = R.layout.item_holiday

    override fun bind(viewHolder: ViewHolder, position: Int) {
        with(viewHolder.itemView) {
            holidayName.text = title
            holidayDate.text = date
        }
    }

}