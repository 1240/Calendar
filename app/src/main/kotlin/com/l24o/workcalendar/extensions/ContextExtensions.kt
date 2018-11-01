package com.l24o.workcalendar.extensions

import android.content.Context
import androidx.core.content.ContextCompat


fun Context.getCompatColor(colorResId: Int): Int {
    return ContextCompat.getColor(this, colorResId)
}