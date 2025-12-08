package com.bettr.adapters.http.bettr.handler.request

import kotlinx.serialization.Serializable

@Serializable
data class LoginHttpRequest(
    val nickname: String,
    val password: String
)

