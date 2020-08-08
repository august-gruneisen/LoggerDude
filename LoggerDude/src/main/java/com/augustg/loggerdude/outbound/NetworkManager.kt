package com.augustg.loggerdude.outbound

import com.augustg.loggerdude.logs.FormattedLog
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Class to handle outgoing network calls
 *
 * @property endpoint The API endpoint to send Logs
 */
class NetworkManager(private val endpoint: String) {

    init {
        Client.config(endpoint)
    }

    private val scope = CoroutineScope(Dispatchers.IO)

    /**
     * Sends Logs to the endpoint
     *
     * @param logs
     * @param callback [NetworkCallback]
     */
    fun sendLogs(logs: List<FormattedLog>, callback: NetworkCallback?) {
        scope.launch {
            when (Client.post(logs)) {
                HttpStatusCode.OK -> callback?.onSucceededSendingLogs()
                else -> callback?.onFailedSendingLogs()
            }
        }
    }
}