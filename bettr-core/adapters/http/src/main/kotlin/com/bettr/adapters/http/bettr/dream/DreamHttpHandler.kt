package com.bettr.adapters.http.bettr.dream

import com.bettr.application.dream.CreateDreamsCommand
import com.bettr.application.dream.CreateDreamsCommandHandler
import com.bettr.domain.dream.DreamRepository
import com.bettr.domain.dream.DreamType
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.buildAndAwait
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

class DreamHttpHandler(
    private val createDreamsCommandHandler: CreateDreamsCommandHandler,
    private val dreamRepository: DreamRepository
) {
    suspend fun getDreamTypes(req: ServerRequest): ServerResponse {
        val types = DreamType.values().map {
            mapOf(
                "key" to it.name,
                "label" to it.label,
                "emoji" to it.emoji
            )
        }
        return ok().bodyValueAndAwait(types)
    }

    suspend fun createDreams(req: ServerRequest): ServerResponse {
        val request = req.awaitBody<List<CreateDreamRequest>>()
        val command = CreateDreamsCommand(
            dreams = request.map {
                CreateDreamsCommand.DreamCommand(
                    accountId = it.accountId.toString(),
                    title = it.title,
                    targetAmount = it.targetAmount,
                    deadline = it.deadline
                )
            }
        )
        createDreamsCommandHandler.execute(command)
        return ok().buildAndAwait()
    }

    suspend fun getDreams(req: ServerRequest): ServerResponse {
        val accountId = req.queryParam("account_id").orElse(null)
        val dreamId = req.queryParam("dreamId").orElse(null)

        if (accountId == null) {
            return ServerResponse.badRequest().bodyValueAndAwait("Missing account_id query parameter")
        }

        try {
            UUID.fromString(accountId)
        } catch (e: IllegalArgumentException) {
            return ServerResponse.badRequest().bodyValueAndAwait("Invalid account_id format. Must be UUID.")
        }

        return if (dreamId != null) {
            val dream = dreamRepository.findByAccountIdAndDreamId(accountId, dreamId)
            if (dream != null) {
                ok().bodyValueAndAwait(dream)
            } else {
                ServerResponse.notFound().buildAndAwait()
            }
        } else {
            val dreams = dreamRepository.findByAccountId(accountId)
            ok().bodyValueAndAwait(dreams)
        }
    }
}

data class CreateDreamRequest(
    val accountId: UUID,
    val title: String,
    val targetAmount: BigDecimal,
    val deadline: LocalDate
)

