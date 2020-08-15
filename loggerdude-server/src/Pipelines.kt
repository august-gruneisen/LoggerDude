package com.augustg

import com.augustg.model.FormattedLog
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.auth.UserIdPrincipal
import io.ktor.auth.principal
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondRedirect
import io.ktor.sessions.sessions
import io.ktor.sessions.set
import io.ktor.util.pipeline.PipelineContext

internal suspend fun PipelineContext<Unit, ApplicationCall>.loginPagePipeline() {
    val error = if ("invalid" in call.request.queryParameters) "Invalid credentials" else ""
    val loginPage = Server.loginPage(error)
    call.respond(loginPage)
}

internal suspend fun PipelineContext<Unit, ApplicationCall>.loginPipeline() {
    // if you made it here, you are authenticated
    val principal = call.principal<UserIdPrincipal>()!!
    Server.login(principal)
    call.apply {
        sessions.set(principal)
        respondRedirect(Endpoints.LOGGERDUDE)
    }
}

internal suspend fun PipelineContext<Unit, ApplicationCall>.consolePagePipeline() {
    val consolePage = Server.consolePage()
    call.respond(consolePage)
}

internal suspend fun PipelineContext<Unit, ApplicationCall>.clearLogsPipeline() {
    Server.clearLogs()
    call.respondRedirect(Endpoints.LOGGERDUDE)
}

internal suspend fun PipelineContext<Unit, ApplicationCall>.postLogsPipeline() {
    val logs = call.receive<List<FormattedLog>>()
    Server.postLogs(logs)
    call.response.status(HttpStatusCode.OK)
}