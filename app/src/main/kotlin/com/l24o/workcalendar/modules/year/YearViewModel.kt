package com.l24o.workcalendar.modules.year

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.l24o.findmylove.base.Event
import com.xwray.groupie.Group
import org.threeten.bp.LocalDate
import org.threeten.bp.Month

class YearViewModel : ViewModel() {

    val itemsEvent = MediatorLiveData<List<Group>>()
    val itemClickEvent = MutableLiveData<Event<Int>>()

    init {
        itemsEvent.value = Month.values().map { it.value }.map { monthValue ->
            val localDate = LocalDate.of(LocalDate.now().year, monthValue, 1)
            YearItem(
                    currentDate = localDate.withDayOfMonth(1),
                    minimumDate = localDate.withDayOfMonth(1),
                    maximumDate = localDate.withDayOfMonth(localDate.lengthOfMonth()),
                    onClickListener = { itemClickEvent.value = Event(monthValue) }
            )
        }
    }

}