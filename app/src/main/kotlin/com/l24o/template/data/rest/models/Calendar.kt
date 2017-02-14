package com.l24o.template.data.rest.models

/**
 * @author Alexander Popov on 14/02/2017.
 */
data class Calendar(
        val holidays: List<Holiday>,
        val days: List<Day>
)

data class Holiday(
        val id: Int,
        val title: String
)

data class Day(
        val d: String,
        val t: Int,
        val h: Int
)