package com.l24o.template.extensions

import android.app.Activity
import android.support.design.widget.Snackbar
import com.afollestad.materialdialogs.MaterialDialog
import com.l24o.template.R
import org.jetbrains.anko.contentView

inline fun Activity.materialDialog(setupBlock: (MaterialDialog.Builder.() -> (MaterialDialog.Builder))): MaterialDialog.Builder {
    return setupBlock.invoke(MaterialDialog.Builder(this))
}

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