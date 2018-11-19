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

    fun calendar(): Single<List<Calendar>> {
        return Single.fromCallable {
            context.assets.list("calendar").map { item ->
                val matcher = RegistryMatcher()
                matcher.bind(TypeOfDay::class.java, TypeOfDayTransformer())
                matcher.bind(LocalDate::class.java, LocalDateTransformer(item.removeSuffix(".xml").toInt()))
                Persister(matcher).read(Calendar::class.java, context.assets.open("calendar/$item"))
            }
        }
    }

}