package com.l24o.workcalendar.modules.view

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout


class SquareConstrainLayout : ConstraintLayout {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {}

    override fun onMeasure(width: Int, height: Int) {
        // note we are applying the width value as the height
        super.onMeasure(width, width)
    }

}