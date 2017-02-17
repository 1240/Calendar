package com.l24o.workcalendar.extensions

import android.app.Activity
import android.support.design.widget.Snackbar
import com.l24o.workcalendar.R
import org.jetbrains.anko.contentView

fun Activity.snackBar(text: String,
                      duration: Int = Snackbar.LENGTH_INDEFINITE,
                      actionText: String = getString(R.string.ok),
                      action: ((Snackbar) -> Unit)? = { it.dismiss() }) {

    contentView?.let {
        val snackBar = Snackbar.make(it, text, duration)
        if (action != null) {
            snackBar.setAction(actionText) { action.invoke(snackBar) }
        }
        snackBar.show()
    }
}