package com.l24o.template.modules.calendar

import com.l24o.stels.common.mvp.RxPresenter
import com.l24o.stels.extensions.parsedMessage
import com.l24o.template.data.rest.repositories.CalendarRepository
import rx.lang.kotlin.plusAssign
import javax.inject.Inject


class CalendarPresenter @Inject constructor(view: ICalendarView) : RxPresenter<ICalendarView>(view), ICalendarPresenter {

    @Inject lateinit var calendarRepository: CalendarRepository

    override fun onViewAttached() {
        super.onViewAttached()
        view?.setLoadingVisible(true)
        subscriptions += calendarRepository.calendar()
                .subscribe(
                        { response ->
                            view?.setLoadingVisible(false)
                        },
                        { error ->
                            view?.showMessage(error.parsedMessage())
                            view?.setLoadingVisible(false)
                        }
                )
    }

}