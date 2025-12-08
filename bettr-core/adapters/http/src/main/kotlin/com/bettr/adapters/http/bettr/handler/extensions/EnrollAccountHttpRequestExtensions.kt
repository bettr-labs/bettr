package com.bettr.adapters.http.bettr.handler.extensions

import com.bettr.adapters.http.bettr.handler.request.EnrollAccountHttpRequest
import com.bettr.application.EnrollAccountCommand
import java.util.UUID

fun EnrollAccountHttpRequest.toCommand(): EnrollAccountCommand = EnrollAccountCommand(
    commandId = UUID.randomUUID().toString(),
    occurredOn = kotlinx.datetime.Clock.System.now(),
    metadata = emptyMap(),
    aggregateId = UUID.randomUUID().toString(),
    nickname = this.nickname,
    password = this.password
)
