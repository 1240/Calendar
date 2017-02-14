package com.l24o.template.modules.calendar

import com.l24o.stels.common.mvp.IPresenter
import com.l24o.stels.common.mvp.IView


interface ICalendarView : IView {
    fun setLoadingVisible(isVisible: Boolean)
}

interface ICalendarPresenter : IPresenter<ICalendarView> {
}

