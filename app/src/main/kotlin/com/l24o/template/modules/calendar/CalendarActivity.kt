package com.l24o.template.modules.calendar

import android.os.Bundle
import android.view.View
import com.l24o.stels.di.AppComponent
import com.l24o.template.R
import com.l24o.template.common.inDebugMode
import com.l24o.template.common.mvp.MvpActivity
import kotlinx.android.synthetic.main.activity_calendar.*
import org.jetbrains.anko.enabled
import org.jetbrains.anko.onClick
import javax.inject.Inject

class CalendarActivity : MvpActivity(), ICalendarView {

    @Inject lateinit var presenter: ICalendarPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)
        initViews()
        presenter.onViewAttached()
    }

    override fun resolveDependencies(appComponent: AppComponent) {
        DaggerSignInComponent.builder()
                .appComponent(appComponent)
                .signInModule(SignInModule(this))
                .build()
                .inject(this)
    }

    override fun beforeDestroy() {
        presenter.dropView()
    }

    override fun setLoadingVisible(isVisible: Boolean) {

    }

    private fun initViews() {
    }
}

