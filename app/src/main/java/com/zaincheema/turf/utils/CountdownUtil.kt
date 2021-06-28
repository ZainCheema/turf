package com.zaincheema.turf.utils

import java.util.concurrent.TimeUnit

object CountdownUtil {
    // A 3 second countdown
    const val COUNTDOWN: Long = 3000L
    private const val TIME_FORMAT = "%d"

    // Convert from millis to seconds
    fun Long.formatTime(): String = String.format(
        TIME_FORMAT,
        TimeUnit.MILLISECONDS.toSeconds(this) % 60
    )

}