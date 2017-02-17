package com.l24o.workcalendar.common.mvp

import com.l24o.workcalendar.CalendarApplication

interface IView {
    fun application(): CalendarApplication
    fun showMessage(message: String)
    fun showMessage(messageResId: Int)
}


interface IPresenter<in V : IView> {
    fun takeView(view: V)
    fun onViewAttached()
    fun dropView()
    fun onViewDetached()
}
