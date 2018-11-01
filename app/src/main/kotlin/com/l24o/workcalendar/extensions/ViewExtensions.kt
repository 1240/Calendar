package com.l24o.workcalendar.extensions

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView


fun RecyclerView.addOffsets(
        offset: Int,
        columns: Int = 1
) {
    addItemDecoration(object : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
        ) {
            val itemPosition = parent.getChildAdapterPosition(view)
            val itemRow = itemPosition / columns

            val firstRow = itemRow == 0
            val firstInRow = itemPosition % columns == 0
            val lastInRow = itemPosition % columns == columns - 1

            val left = if (firstInRow) offset else 0
            val top = if (firstRow) offset else 0
            val right = offset
            val bottom = offset

            outRect.set(left, top, right, bottom)
        }
    })
}