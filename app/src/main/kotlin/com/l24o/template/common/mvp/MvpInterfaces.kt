package com.l24o.stels.common.mvp

import com.l24o.template.CalendarApplication

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
