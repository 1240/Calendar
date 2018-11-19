package com.l24o.workcalendar.extensions

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

fun RecyclerView.addYearOffsets(
        offset: Int,
        columns: Int = 1
) {
    addItemDecoration(object : RecyclerView.ItemDecoration() {

        var fullSizeSpan = hashSetOf<Int>()

        override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
        ) {
            val itemPosition = parent.getChildAdapterPosition(view)
            val isCurrentFullSizeSpan = (parent.getChildAt(itemPosition)?.layoutParams as? StaggeredGridLayoutManager.LayoutParams)?.isFullSpan
                    ?: false
            val isPrevFullSizeSpan = (parent.getChildAt(itemPosition - 1)?.layoutParams as? StaggeredGridLayoutManager.LayoutParams)?.isFullSpan
                    ?: false
            when {
                isCurrentFullSizeSpan -> {
                    fullSizeSpan.add(itemPosition)
                    outRect.set(offset, offset, offset, offset)
                }
                !isPrevFullSizeSpan -> {
                    val itemRow = (itemPosition - fullSizeSpan.size) / columns

                    val firstRow = itemRow == 0
                    val firstInRow = (itemPosition - fullSizeSpan.size) % columns == 0

                    val left = if (firstInRow) offset else 0
                    val top = if (firstRow) offset else 0
                    val right = offset
                    val bottom = offset

                    outRect.set(left, top, right, bottom)
                }
                isPrevFullSizeSpan -> {
                    outRect.set(offset, 0, offset, offset)
                }
            }
        }
    })
}