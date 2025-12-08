package com.bettr.adapters.http.bettr.handler

import com.bettr.adapters.http.bettr.handler.extensions.toCommand
import com.bettr.adapters.http.bettr.handler.request.EnrollAccountHttpRequest
import com.bettr.application.EnrollAccountCommand
import com.bettr.application.EnrollAccountCommandHandler
import org.springframework.http.HttpStatus.CREATED
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.status
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.buildAndAwait

class EnrollAccountHttpHandler(
    private val enrollAccountCommandHandler: EnrollAccountCommandHandler
) {
    suspend fun execute(req: ServerRequest): ServerResponse {
        enrollAccountCommandHandler.execute(req.toCommand())
        return status(CREATED).buildAndAwait()
    }

    private suspend fun ServerRequest.toCommand(): EnrollAccountCommand =
        awaitBody<EnrollAccountHttpRequest>().toCommand()
}
