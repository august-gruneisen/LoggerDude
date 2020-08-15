package com.augustg

object Credentials {
    val username: String = System.getenv("LOGGERDUDE_USERNAME")
    val password: String = System.getenv("LOGGERDUDE_PASSWORD")
}

object Endpoints {
    const val STATIC = "/static"
    const val ROOT = "/"
    const val LOGGERDUDE = "/loggerdude"
    const val LOGIN = "/login"
    const val LOG = "/log"
    const val CLEAR = "/clear"
}

object Urls {
    const val LOGIN = "login"
}

object Env {
    var DEBUG = false

    val connectionString: String = System.getenv("LOGGERDUDE_CONNECTION_STRING")
    val databaseName: String = System.getenv("LOGGERDUDE_DATABASE_NAME")
    val collectionName: String = System.getenv("LOGGERDUDE_COLLECTION_NAME")
    val collectionNameDebug: String = System.getenv("LOGGERDUDE_COLLECTION_NAME_DEBUG")
}