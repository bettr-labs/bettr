package com.bettr.adapters.http.bettr.handler

import com.bettr.application.DeactivateAccountCommandHandler
import com.bettr.application.GetAccountQueryHandler
import com.bettr.application.UpdateAccountCommand
import com.bettr.application.UpdateAccountCommandHandler
import com.bettr.domain.Account
import com.bettr.domain.AccountStatus
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.buildAndAwait
import java.time.Instant
import java.util.UUID

class AccountHttpHandler(
    private val getAccountQueryHandler: GetAccountQueryHandler,
    private val updateAccountCommandHandler: UpdateAccountCommandHandler,
    private val deactivateAccountCommandHandler: DeactivateAccountCommandHandler
) {
    suspend fun getAccount(req: ServerRequest): ServerResponse {
        val accountId = req.queryParam("accountId").orElse(null)
            ?: return ServerResponse.badRequest().bodyValueAndAwait("Missing accountId query parameter")
        
        validateUUID(accountId, "accountId")

        val account = getAccountQueryHandler.execute(accountId)
        return if (account != null) {
            val response = AccountResponse(
                id = account.id,
                nickname = account.nickname,
                createdAt = account.createdAt,
                status = account.status
            )
            ok().bodyValueAndAwait(response)
        } else {
            ServerResponse.notFound().buildAndAwait()
        }
    }

    suspend fun updateAccount(req: ServerRequest): ServerResponse {
        val accountId = req.queryParam("accountId").orElse(null)
            ?: return ServerResponse.badRequest().bodyValueAndAwait("Missing accountId query parameter")

        validateUUID(accountId, "accountId")
        
        val request = req.awaitBody<UpdateAccountRequest>()
        val command = UpdateAccountCommand(
            accountId = accountId,
            nickname = request.nickname
        )

        try {
            updateAccountCommandHandler.execute(command)
            return ok().buildAndAwait()
        } catch (e: IllegalArgumentException) {
            return ServerResponse.notFound().buildAndAwait()
        }
    }

    suspend fun deactivateAccount(req: ServerRequest): ServerResponse {
        val accountId = req.queryParam("accountId").orElse(null)
            ?: return ServerResponse.badRequest().bodyValueAndAwait("Missing accountId query parameter")

        validateUUID(accountId, "accountId")

        try {
            deactivateAccountCommandHandler.execute(accountId)
            return ok().buildAndAwait()
        } catch (e: IllegalArgumentException) {
            return ServerResponse.notFound().buildAndAwait()
        }
    }

    private fun validateUUID(id: String, fieldName: String) {
        try {
            UUID.fromString(id)
        } catch (e: Exception) {
            throw IllegalArgumentException("Invalid $fieldName format. Must be UUID.")
        }
    }
}

data class AccountResponse(
    val id: String,
    val nickname: String,
    val createdAt: Instant,
    val status: AccountStatus
)

data class UpdateAccountRequest(
    val nickname: String? = null
)

