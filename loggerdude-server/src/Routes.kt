package com.augustg

import io.ktor.auth.authenticate
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post

internal fun Routing.staticFilesRoute() {
    static(Endpoints.STATIC) {
        resources("static")
    }
}

internal fun Routing.loginPage() {
    get(Endpoints.LOGIN) {
        loginPagePipeline()
    }
}

internal fun Routing.submitLoginRoute() {
    authenticate("form") {
        post(Endpoints.LOGIN) {
            loginPipeline()
        }
    }
}

internal fun Routing.consoleRoute() {
    authenticate("session") {
        get(Endpoints.ROOT) {
            consolePagePipeline()
        }
    }
}

// TODO install header auth to post logs
internal fun Routing.postLogsRoute() {
    post(Endpoints.LOG) {
        postLogsPipeline()
    }
}

// TODO install header auth to clear logs
internal fun Routing.clearLogsRoute() {
    get(Endpoints.CLEAR) {
        clearLogsPipeline()
    }
}