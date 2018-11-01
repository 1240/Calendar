package com.l24o.workcalendar.data.rest

import com.l24o.workcalendar.Constants
import com.l24o.workcalendar.data.rest.models.TypeOfDay
import org.simpleframework.xml.transform.Transform
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import java.text.SimpleDateFormat
import java.util.*

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

class LocalDateTransformer(val year: Int) : Transform<LocalDate> {
    override fun read(value: String): LocalDate {
        val date = SimpleDateFormat(Constants.CALENDAR_DAY_FORMAT, Locale.getDefault()).parse(value)
        val cal = Calendar.getInstance().apply {
            time = date
        }
        return LocalDate.of(year, cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH))
    }

    override fun write(value: LocalDate): String {
        return value.format(DateTimeFormatter.ofPattern(Constants.CALENDAR_DAY_FORMAT))
    }

}