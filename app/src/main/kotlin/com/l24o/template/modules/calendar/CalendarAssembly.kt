package com.l24o.template.modules.calendar

import com.l24o.template.di.scopes.ActivityScope
import com.l24o.stels.di.AppComponent
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides

@ActivityScope
@Component(modules = arrayOf(CalendarModule::class), dependencies = arrayOf(AppComponent::class))
interface CalendarComponent {
    fun inject(calendarActivity: CalendarActivity)
}

@Module(includes = arrayOf(CalendarModule.Declarations::class))
class CalendarModule(val calendarView: ICalendarView) {

    @Provides
    fun provideCalendarView(): ICalendarView {
        return calendarView
    }

    @Module
    interface Declarations {

        @Binds
        @ActivityScope
        fun bindCalendarPresenter(calendarPresenter: CalendarPresenter): ICalendarPresenter
    }
}

