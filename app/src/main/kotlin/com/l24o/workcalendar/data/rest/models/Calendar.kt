package com.l24o.workcalendar.data.rest.models

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import java.util.*

/**
 * @author Alexander Popov on 14/02/2017.
 */
@Root(name = "calendar")
data class Calendar(
        @field:ElementList(entry = "holidays")
        var holidays: List<Holiday>? = null,
        @field:ElementList(name = "days")
        var days: List<Day>? = null,
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
        var id: String? = null,
        @field:Attribute(name = "title")
        var title: String? = null,
        var date: ArrayList<Date> = ArrayList()
)

@Element(name = "day")
data class Day(
        @field:Attribute(name = "d")
        var day: Date? = null,
        @field:Attribute(name = "t")
        var type: TypeOfDay? = null,
        @field:Attribute(name = "h", empty = "", required = false)
        var holidayId: String? = null
)

enum class TypeOfDay {
    NONE,
    REST_DAY,
    SHORT_DAY,
    WORK_DAY //если суббота или воскресенье
}