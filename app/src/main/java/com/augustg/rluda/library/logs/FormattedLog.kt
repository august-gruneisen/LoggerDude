package com.augustg.rluda.library.logs

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
        return "$timestamp: $message"
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

        /**
         * Formats a list of Logs
         *
         * @param logs
         * @param dateFormat (optional) specified [DateFormat] for the timestamp
         * @return list of formatted Logs
         */
        fun format(
            logs: List<Log>,
            dateFormat: DateFormat = DateFormat.getDateTimeInstance()
        ): List<FormattedLog> {
            return logs.map { log ->
                FormattedLog(
                    timestamp = dateFormat.format(Date(log.time)),
                    message = log.message
                )
            }
        }
    }
}
