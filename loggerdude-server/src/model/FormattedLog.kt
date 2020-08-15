package com.augustg.model

import kotlinx.serialization.Serializable

@Serializable
data class FormattedLog(
    val timestamp: String,
    val message: String
)