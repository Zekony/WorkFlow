package com.finto.utility.functions

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.Month
import java.time.ZoneOffset

fun Long.toDate(): String {
    val localDateTime = LocalDateTime.ofEpochSecond(this, 0, ZoneOffset.UTC)
    val month = Month.of(localDateTime.monthValue)
    return "${localDateTime.dayOfMonth} ${
        month.toString().lowercase().replaceFirstChar { it.uppercase() }
    }"
}

fun getCurrentTimeInES(): Long {
    return LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
}

fun Long.toTime(): String {
    val localTime = LocalDateTime.ofEpochSecond(this, 0, ZoneOffset.UTC).toLocalTime()
    val minute =
        if (localTime.minute.toString().length == 1) '0' + localTime.minute.toString() else localTime.minute
    return "${localTime.hour}:${minute}"
}

fun Long.toFullDate(): String {
    val localDateTime = LocalDateTime.ofEpochSecond(this, 0, ZoneOffset.UTC)
    return "${localDateTime.dayOfMonth}/${localDateTime.monthValue}/${localDateTime.year}"
}

fun setTimeLogic(stateTime: Long, time: Long): Long {
    val dateAndTime = stateTime.distinctLDandLT()
    val isSetTimeDate = time >= 86400
    return when {
        isSetTimeDate -> {
            time + dateAndTime.second
        }

        time == 0L -> {
            dateAndTime.first
        }

        else -> {
            time + dateAndTime.first
        }
    }
}

fun Long.distinctLDandLT(): Pair<Long, Long> {
    val date = LocalDateTime.ofEpochSecond(
        this,
        0,
        ZoneOffset.UTC
    ).toLocalDate().toEpochSecond(
        LocalTime.MIDNIGHT, ZoneOffset.UTC
    )
    val time = this - date
    return Pair(date, time)
}

fun Long.distinctHoursAndMinutes(): Pair<Long, Long> {
    val date = LocalTime.ofSecondOfDay(this)
    val hours = date.hour * 3600L
    val minutes = date.minute * 60L

    return Pair(hours, minutes)
}


fun getYearRange(yearSpan: Int) : IntRange {
    val currentYear = LocalDate.now().year
    return currentYear - yearSpan .. currentYear + yearSpan
}