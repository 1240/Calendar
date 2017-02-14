package com.l24o.template.common

import com.l24o.template.BuildConfig

inline fun inDebugMode(block: (() -> Unit)) {
    if (BuildConfig.DEBUG) {
        block.invoke()
    }
}
