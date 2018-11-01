package com.l24o.findmylove.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment

abstract class BaseDialogFragment : DialogFragment() {

    companion object {
        private const val REQUIRED_WIDTH_IN_PERCENT = 1
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.let {
            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            it.setLayout((resources.displayMetrics.widthPixels * REQUIRED_WIDTH_IN_PERCENT).toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        var anyParentIsRemoving = false

        if (Build.VERSION.SDK_INT >= 21) {
            var parent: Fragment? = parentFragment
            while (!anyParentIsRemoving && parent != null) {
                anyParentIsRemoving = parent.isRemoving
                parent = parent.parentFragment
            }
        }
    }
}