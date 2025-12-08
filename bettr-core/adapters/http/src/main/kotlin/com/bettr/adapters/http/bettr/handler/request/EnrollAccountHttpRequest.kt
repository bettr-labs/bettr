package com.bettr.adapters.http.bettr.handler.request

import kotlinx.serialization.Serializable

@Serializable
data class EnrollAccountHttpRequest(
    val nickname: String
)
