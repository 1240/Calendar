package com.l24o.workcalendar.modules.calendar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.l24o.workcalendar.R
import com.l24o.workcalendar.modules.month.MonthFragment
import com.l24o.workcalendar.modules.year.YearFragment
import kotlinx.android.synthetic.main.activity_calendar.*
import org.threeten.bp.LocalDate


class CalendarActivity : AppCompatActivity() {

    private lateinit var viewModel: CalendarViewModel
    private var pagerAdapter: CalendarPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)
        viewModel = ViewModelProviders.of(this).get(CalendarViewModel::class.java)

        initViews()

        viewModel.currentDay.observe(this, Observer {
            activity_calendar_text_view_today_day.text = it.first
            activity_calendar_text_view_today_month.text = it.second
        })
    }

    private fun initViews() {
        activity_calendar_text_view_month.setOnClickListener {
            if (!it.isSelected) {
                activity_calendar_text_view_year.isSelected = false
                activity_calendar_text_view_month.isSelected = true
                activity_calendar_container.setCurrentItem(0, true)
            }
        }

        activity_calendar_text_view_year.setOnClickListener {
            if (!it.isSelected) {
                activity_calendar_text_view_month.isSelected = false
                activity_calendar_text_view_year.isSelected = true
                activity_calendar_container.setCurrentItem(1, true)
            }
        }
        activity_calendar_container_today.setOnClickListener {
            activity_calendar_container.setCurrentItem(0, true)
            pagerAdapter?.monthFragment?.scrollTo(LocalDate.now().monthValue)
        }
        with(activity_calendar_container) {
            pagerAdapter = CalendarPagerAdapter(supportFragmentManager) {
                setCurrentItem(it, false)
            }
            adapter = pagerAdapter
            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {
                }

                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                }

                override fun onPageSelected(position: Int) {
                    when (position) {
                        0 -> {
                            activity_calendar_text_view_year.isSelected = false
                            activity_calendar_text_view_month.isSelected = true
                        }
                        else -> {
                            activity_calendar_text_view_month.isSelected = false
                            activity_calendar_text_view_year.isSelected = true
                        }
                    }
                }

            })
        }

        activity_calendar_text_view_year.isSelected = false
        activity_calendar_text_view_month.isSelected = true
    }
}

class CalendarPagerAdapter(
        fragmentManager: FragmentManager,
        val setCurrentItem: (Int) -> Unit
) : FragmentPagerAdapter(fragmentManager) {
    val monthFragment by lazy { MonthFragment() }
    val yearFragment by lazy {
        YearFragment().also {
            it.onMonthClick = { month: Int ->
                monthFragment.scrollTo(month)
                setCurrentItem.invoke(0)
            }
        }
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> monthFragment
            else -> yearFragment
        }
    }

    override fun getCount() = 2
}
