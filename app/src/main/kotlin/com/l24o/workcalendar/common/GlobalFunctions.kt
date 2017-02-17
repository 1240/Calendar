package com.l24o.workcalendar.common

import com.l24o.workcalendar.BuildConfig

inline fun inDebugMode(block: (() -> Unit)) {
    if (BuildConfig.DEBUG) {
        block.invoke()
    }
}
