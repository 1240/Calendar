package com.l24o.workcalendar.data.rest.models

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import org.threeten.bp.LocalDate
import java.util.*

@Root(name = "calendar")
data class Calendar(
        @field:ElementList(entry = "holidays")
        var holidays: List<Holiday> = mutableListOf(),
        @field:ElementList(name = "days")
        var days: List<Day> = mutableListOf(),
        @field:Attribute(name = "year")
        var year: String? = null,
        @field:Attribute(name = "lang")
        var lang: String? = null,
        @field:Attribute(name = "date")
        var date: String? = null
)

@Element(name = "holiday")
data class Holiday(
        @field:Attribute(name = "id")
        var id: String = "",
        @field:Attribute(name = "title")
        var title: String = "",
        var date: ArrayList<LocalDate> = ArrayList()
) {
    override fun equals(other: Any?): Boolean {
        return other is Holiday && id == other.id
    }
}

@Element(name = "day")
data class Day(
        @field:Attribute(name = "d")
        var day: LocalDate = LocalDate.now(),
        @field:Attribute(name = "t")
        var type: TypeOfDay = TypeOfDay.NONE,
        @field:Attribute(name = "h", required = false)
        var holidayId: String? = null
)

enum class TypeOfDay {
    NONE,
    REST_DAY,
    SHORT_DAY,
    WORK_DAY //если суббота или воскресенье
}