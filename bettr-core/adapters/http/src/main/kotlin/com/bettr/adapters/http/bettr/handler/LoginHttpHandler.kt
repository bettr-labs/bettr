package com.bettr.adapters.http.bettr.handler

import com.bettr.adapters.http.bettr.handler.request.LoginHttpRequest
import com.bettr.application.LoginCommand
import com.bettr.application.LoginCommandHandler
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.buildAndAwait

import java.util.UUID

class LoginHttpHandler(
    private val loginCommandHandler: LoginCommandHandler
) {
    suspend fun execute(req: ServerRequest): ServerResponse {
        val request = req.awaitBody<LoginHttpRequest>()
        
        return try {
            val accountId = loginCommandHandler.execute(
                LoginCommand(
                    nickname = request.nickname,
                    password = request.password
                )
            )
            ServerResponse.ok().bodyValueAndAwait(LoginResponse(UUID.fromString(accountId)))
        } catch (e: IllegalArgumentException) {
            ServerResponse.status(HttpStatus.UNAUTHORIZED).bodyValueAndAwait(mapOf("reason" to e.message))
        }
    }
}

data class LoginResponse(val accountId: UUID)