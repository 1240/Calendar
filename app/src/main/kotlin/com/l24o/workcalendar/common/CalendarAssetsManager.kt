package com.l24o.workcalendar.common

import com.l24o.workcalendar.CalendarApplication.Companion.context
import com.l24o.workcalendar.data.rest.LocalDateTransformer
import com.l24o.workcalendar.data.rest.TypeOfDayTransformer
import com.l24o.workcalendar.data.rest.models.Calendar
import com.l24o.workcalendar.data.rest.models.TypeOfDay
import io.reactivex.Single
import org.simpleframework.xml.core.Persister
import org.simpleframework.xml.transform.RegistryMatcher
import org.threeten.bp.LocalDate

object CalendarAssetsManager {

    fun calendar(year: Int): Single<Calendar> {
        return Single.fromCallable {
            val matcher = RegistryMatcher()
            matcher.bind(TypeOfDay::class.java, TypeOfDayTransformer())
            matcher.bind(LocalDate::class.java, LocalDateTransformer(year))
            Persister(matcher).read(Calendar::class.java, context.assets.open("calendar_$year.xml"))
        }
    }

}