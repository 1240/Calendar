package com.l24o.workcalendar.common.mvp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.l24o.workcalendar.CalendarApplication
import com.l24o.workcalendar.common.mvp.IView
import com.l24o.workcalendar.di.AppComponent
import org.jetbrains.anko.toast

abstract class MvpActivity: AppCompatActivity(), IView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        resolveDependencies(application().appComponent)
    }

    override fun onDestroy() {
        beforeDestroy()
        super.onDestroy()
    }

    abstract fun resolveDependencies(appComponent: AppComponent)

    abstract fun beforeDestroy()

    override fun application(): CalendarApplication {
        return application as CalendarApplication
    }

    override fun showMessage(message: String) {
        toast(message)
    }

    override fun showMessage(messageResId: Int) {
        toast(messageResId)
    }

}