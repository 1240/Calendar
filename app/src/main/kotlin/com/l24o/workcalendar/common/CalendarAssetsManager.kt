package com.l24o.workcalendar.common

import android.content.Context
import com.l24o.workcalendar.data.rest.DateTransformer
import com.l24o.workcalendar.data.rest.TypeOfDayTransformer
import com.l24o.workcalendar.data.rest.models.Calendar
import com.l24o.workcalendar.data.rest.models.TypeOfDay
import org.simpleframework.xml.core.Persister
import org.simpleframework.xml.transform.RegistryMatcher
import rx.Observable
import java.util.*
import javax.inject.Inject

/**
 * @author Alexander Popov on 15/02/2017.
 */
class CalendarAssetsManager @Inject constructor(private val context: Context) {

    fun calendar(): Observable<Calendar> {
        val matcher = RegistryMatcher()
        matcher.bind(TypeOfDay::class.java, TypeOfDayTransformer())
        matcher.bind(Date::class.java, DateTransformer())
        return Observable.just(Persister(matcher).read(Calendar::class.java, context.assets.open("calendar.xml")))
    }

}