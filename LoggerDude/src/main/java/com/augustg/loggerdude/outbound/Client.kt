package com.augustg.loggerdude.outbound

import com.augustg.loggerdude.logs.FormattedLog
import io.ktor.client.HttpClient
import io.ktor.client.features.DefaultRequest
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode

/**
 * Network client for sending outbound Logs to a server
 */
object Client {

    private var client = build("http://default")

    /**
     * Called to specify an API endpoint for sending Logs
     *
     * @param endpoint
     */
    fun config(endpoint: String) {
        client = build(endpoint)
    }

    /**
     * Builder method for changing the API endpoint
     *
     * @param endpoint
     */
    private fun build(endpoint: String): HttpClient {
        return HttpClient {
            install(JsonFeature) {
                serializer = GsonSerializer {
                    serializeNulls()
                    disableHtmlEscaping()
                }
            }
            install(DefaultRequest) {
                url(endpoint)
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }
    }

    /**
     * Posts Logs to the specified API endpoint
     *
     * @param logs
     * @return [HttpStatusCode]
     */
    suspend fun post(logs: List<FormattedLog>): HttpStatusCode {
        return client.post<HttpResponse>(body = logs).status
    }

    /**
     * Should be called when the app no longer needs to send outgoing Logs
     */
    fun close() {
        client.close()
    }
}