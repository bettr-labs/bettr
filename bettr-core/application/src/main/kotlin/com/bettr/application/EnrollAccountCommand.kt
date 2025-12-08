package com.bettr.application

import kotlinx.datetime.Instant


data class EnrollAccountCommand(
    val commandId: String,
    val occurredOn: Instant,
    val metadata: Map<String, String>,
    val aggregateId: String,
    val nickname: String,
    val password: String
)
