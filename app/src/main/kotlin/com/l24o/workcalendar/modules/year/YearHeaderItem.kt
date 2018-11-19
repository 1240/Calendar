package com.l24o.workcalendar.modules.year

import com.l24o.workcalendar.R
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.item_year_header.view.*
import androidx.recyclerview.widget.StaggeredGridLayoutManager



class YearHeaderItem(val year: Int) : Item<ViewHolder>(), StickyItem {
    override fun getLayout() = R.layout.item_year_header

    override fun isSticky() = true

    override fun bind(viewHolder: ViewHolder, position: Int) {
        val layoutParams = viewHolder.itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams
        layoutParams.isFullSpan = true
        viewHolder.itemView.item_year_header_text_view.text = "$year"
    }

}

interface StickyItem {
    fun isSticky(): Boolean
}