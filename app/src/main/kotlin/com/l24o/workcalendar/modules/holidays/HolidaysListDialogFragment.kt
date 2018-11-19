package com.l24o.findmylove.modules.dialogs

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.l24o.findmylove.base.BaseDialogFragment
import com.l24o.workcalendar.R
import com.xwray.groupie.Group
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_holiday_list.*

class HolidaysListDialogFragment : BaseDialogFragment() {

    companion object {
        const val TAG = "WatchersListDialogFragment"
        fun newInstance() = HolidaysListDialogFragment()
    }

    private val adapter = GroupAdapter<ViewHolder>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireContext(), R.style.TransparentBottomSheetDialog)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_holiday_list, container, false)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragment_holiday_list_recycler.layoutManager = LinearLayoutManager(view.context)
        fragment_holiday_list_recycler.adapter = adapter
        activity_calendar_container_holidays.setOnClickListener {
            dismiss()
        }
    }

    fun setItems(items: List<Group>) {
        adapter.update(items)
    }
}

