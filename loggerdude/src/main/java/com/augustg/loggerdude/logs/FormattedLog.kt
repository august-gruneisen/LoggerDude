package com.augustg.loggerdude.logs

import java.text.DateFormat
import java.util.*

/**
 * Class representing a formatted [Log]
 *
 * @property timestamp
 * @property message
 */
class FormattedLog private constructor(
    val timestamp: String,
    val message: String
) {

    /**
     * Returns a String representation of the formatted Log
     *
     * @return String Log
     */
    override fun toString(): String {
        return "${simpleTimestamp()} $message"
    }

    private fun simpleTimestamp(): String {
        return timestamp.substring(12, timestamp.length - 3)
    }

    /**
     * Contains methods returning formatted Logs
     */
    companion object {

        /**
         * Formats a Log
         *
         * @param log
         * @param dateFormat (optional) specified [DateFormat] for the timestamp
         * @return formatted Log
         */
        fun format(
            log: Log,
            dateFormat: DateFormat = DateFormat.getDateTimeInstance()
        ): FormattedLog {
            return FormattedLog(
                timestamp = dateFormat.format(Date(log.time)),
                message = log.message
            )
        }
    }
}