package com.l24o.workcalendar.modules.year

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.l24o.workcalendar.R
import com.l24o.workcalendar.extensions.addOffsets
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_year.*

class YearFragment : Fragment() {

    private lateinit var viewModel: YearViewModel
    private val monthsAdapter = GroupAdapter<ViewHolder>()
    var onMonthClick: ((Int) -> Unit)? = null

    companion object {
        private const val COLUMN_COUNT = 2
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_year, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(YearViewModel::class.java)
        initViews()
        subscribeEvents()
    }

    private fun initViews() {
        with(fragment_year_recycler) {
            layoutManager = GridLayoutManager(context, COLUMN_COUNT)
            adapter = monthsAdapter
            addOffsets(
                    offset = resources.getDimensionPixelSize(R.dimen.item_offset),
                    columns = COLUMN_COUNT
            )
        }
    }

    private fun subscribeEvents() {
        viewModel.itemsEvent.observe(this, Observer(monthsAdapter::update))
        viewModel.itemClickEvent.observe(this, Observer {
            it.getContentIfNotHandled()?.let { it1 -> onMonthClick?.invoke(it1) }
        })
    }
}