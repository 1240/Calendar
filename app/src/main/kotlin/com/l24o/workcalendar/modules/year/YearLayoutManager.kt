package com.l24o.workcalendar.modules.year

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.jay.widget.StickyHeaders
import com.jay.widget.StickyHeadersStaggeredGridLayoutManager


class YearLayoutManager<T>(
        columnCount: Int,
        orientation: Int
) : StickyHeadersStaggeredGridLayoutManager<T>(columnCount, orientation) where T : RecyclerView.Adapter<*>, T : StickyHeaders {
//
//    var maxHeight: Int = 0
//        set(value) {
//            if (value > field) {
//                field = value
//            }
//        }
//
//    override fun onItemsAdded(recyclerView: RecyclerView, positionStart: Int, itemCount: Int) {
//        super.onItemsAdded(recyclerView, positionStart, itemCount)
//    }
//
//    override fun getChildAt(index: Int): View? {
//        val child = super.getChildAt(index)
//        updateHeight(child)
//        return child
//    }
//
//    override fun scrollVerticallyBy(dy: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
//        val scrollVerticallyBy = super.scrollVerticallyBy(dy, recycler, state)
//        if (scrollVerticallyBy != 0) {
//            var index = 0
//            while (index < childCount) {
//                val child = this.getChildAt(index)
//                updateHeight(child)
//                ++index
//            }
//        }
//        return scrollVerticallyBy
//
//    }
//
//    private fun updateHeight(child: View?) {
//        val params = child?.layoutParams as? LayoutParams
//        if (params?.isFullSpan != true) {
//            maxHeight = child?.height ?: 0
//            params?.height = maxHeight
//        }
//    }
}