package com.l24o.workcalendar.modules.year

import com.jay.widget.StickyHeaders
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder

class YearAdapter : GroupAdapter<ViewHolder>(), StickyHeaders {

    override fun isStickyHeader(position: Int): Boolean {
        val item = getItem(position)
        return item is StickyItem && (item as StickyItem).isSticky()
    }

}