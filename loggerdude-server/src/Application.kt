package com.augustg

import freemarker.cache.ClassTemplateLoader
import freemarker.template.Configuration
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.freemarker.FreeMarker
import io.ktor.response.respondRedirect
import io.ktor.routing.routing
import io.ktor.serialization.json
import io.ktor.sessions.SessionTransportTransformerMessageAuthentication
import io.ktor.sessions.Sessions
import io.ktor.sessions.cookie
import kotlin.random.Random

/**
 * Entry point specified in resources/application.conf
 */
fun Application.main() {

    // install features
    install(DefaultHeaders)
    install(Authentication) {
        configureFormAuth()
        configureSessionAuth()
    }
    install(Sessions) {
        configureAuthCookie()
    }
    install(FreeMarker) {
        configureTemplateLoader()
    }
    install(ContentNegotiation) {
        json()
    }

    // handle routing
    routing {
        staticFilesRoute()
        loginPage()
        submitLoginRoute()
        consoleRoute()
        postLogsRoute()
        clearLogsRoute()
    }
}

/**
 * Configures the freemarker template loader
 */
private fun Configuration.configureTemplateLoader() {
    templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
}

/**
 * Configures form auth for initial login validation
 */
private fun Authentication.Configuration.configureFormAuth() {
    form("form") {
        userParamName = "username"
        passwordParamName = "password"
        validate { credentials ->
            if (credentials.name == Credentials.username && credentials.password == Credentials.password)
                UserIdPrincipal(credentials.name)
            else null
        }
        challenge {
            when (call.authentication.allFailures.singleOrNull()) {
                AuthenticationFailedCause.InvalidCredentials -> call.respondRedirect("${Urls.LOGIN}?invalid")
                else -> call.respondRedirect(Urls.LOGIN)
            }
        }
    }
}

/**
 * Configures session auth for staying authenticated after login
 */
private fun Authentication.Configuration.configureSessionAuth() {
    session<UserIdPrincipal>("session") {
        validate { session -> // additional principal validation (optional)
            session
        }
        challenge {
            call.respondRedirect(Urls.LOGIN)
        }
    }
}

/**
 * Configures the site-wide session using a cookie
 */
private fun Sessions.Configuration.configureAuthCookie() {
    val secretHashKey = Random.Default.nextBytes(10)
    cookie<UserIdPrincipal>("cookie") {
        cookie.path = Endpoints.ROOT
        transform(SessionTransportTransformerMessageAuthentication(secretHashKey))
    }
}