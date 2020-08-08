package com.augustg.loggerdude.logs

/**
 * Data representation of a Log
 *
 * @param time of Log in milliseconds since midnight, January 1, 1970 UTC
 * @param message
 */
data class Log(
    val time: Long,
    val message: String
)