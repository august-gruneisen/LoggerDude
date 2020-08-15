package com.augustg

import com.augustg.logic.LogOperations
import com.augustg.model.FormattedLog
import io.ktor.auth.UserIdPrincipal
import io.ktor.freemarker.FreeMarkerContent

/**
 * Server operations to handle business logic for pipelines
 */
object Server {

    /**
     * Generates the login page
     *
     * @param error if arriving from a failed login attempt
     */
    fun loginPage(error: String): FreeMarkerContent {
        return FreeMarkerContent(
            template = "login.ftl",
            model = mapOf("error" to error)
        )
    }

    /**
     * Optional server logic on successful login
     *
     * @param principal
     */
    fun login(principal: UserIdPrincipal) {
        val blacklisted = ""
        assert(principal.name != blacklisted)
    }

    /**
     * Gets logs from the request and stores them locally
     *
     * @param logs from request body
     */
    fun postLogs(logs: List<FormattedLog>) {
        LogOperations.storeLogs(logs)
    }

    /**
     * Fetches locally stored logs and generates the console page
     *
     * @return console page with logs
     */
    fun consolePage(): FreeMarkerContent {
        val logs = LogOperations.fetchLogsFormatted()
        return FreeMarkerContent(
            template = "console.ftl",
            model = mapOf("logs" to logs)
        )
    }

    /**
     * Clears locally stored logs
     */
    fun clearLogs() {
        LogOperations.clearLogs()
    }
}