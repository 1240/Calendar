package com.l24o.workcalendar.data.rest

import com.l24o.workcalendar.Constants
import com.l24o.workcalendar.data.rest.models.TypeOfDay
import com.l24o.workcalendar.extensions.toString
import org.simpleframework.xml.transform.Transform
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Alexander Popov on 15/02/2017.
 */

class TypeOfDayTransformer : Transform<TypeOfDay> {
    override fun read(value: String): TypeOfDay {
        return when (value) {
            "1" -> TypeOfDay.REST_DAY
            "2" -> TypeOfDay.SHORT_DAY
            "3" -> TypeOfDay.WORK_DAY
            else -> TypeOfDay.NONE
        }
    }

    override fun write(value: TypeOfDay): String {
        return when (value) {
            TypeOfDay.REST_DAY -> "1"
            TypeOfDay.SHORT_DAY -> "2"
            TypeOfDay.WORK_DAY -> "3"
            else -> "0"
        }
    }

}

class DateTransformer : Transform<Date> {
    override fun read(value: String): Date {
        return SimpleDateFormat(Constants.CALENDAR_DAY_FORMAT, Locale.getDefault()).parse(value)
    }

    override fun write(value: Date): String {
        return value.toString(Constants.CALENDAR_DAY_FORMAT)
    }

}