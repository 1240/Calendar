package com.l24o.stels.common.mvp

import android.app.Fragment
import android.os.Bundle
import com.l24o.template.CalendarApplication
import com.l24o.stels.di.AppComponent
import org.jetbrains.anko.toast


abstract class MvpFragment : Fragment(), IView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependencies(application().appComponent)
    }

    override fun onDestroy() {
        beforeDestroy()
        super.onDestroy()
    }

    abstract fun injectDependencies(appComponent: AppComponent)

    abstract fun beforeDestroy()

    override fun application(): CalendarApplication {
        return activity.application as CalendarApplication
    }

    override fun showMessage(message: String) {
        toast(message)
    }

    override fun showMessage(messageResId: Int) {
        toast(messageResId)
    }

}