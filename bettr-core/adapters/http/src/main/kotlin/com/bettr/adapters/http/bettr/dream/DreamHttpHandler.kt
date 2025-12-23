package com.bettr.adapters.http.bettr.dream

import com.bettr.application.dream.CreateDreamsCommand
import com.bettr.application.dream.CreateDreamsCommandHandler
import com.bettr.application.dream.UpdateDreamCommand
import com.bettr.application.dream.UpdateDreamCommandHandler
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
    private val updateDreamCommandHandler: UpdateDreamCommandHandler,
    private val dreamRepository: DreamRepository
) {
    suspend fun getDreamTypes(req: ServerRequest): ServerResponse {
        val types = DreamType.values().map {
            mapOf(
                "key" to it.name,
                "label" to it.label
            )
        }
        return ok().bodyValueAndAwait(types)
    }

    suspend fun createDreams(req: ServerRequest): ServerResponse {
        val accountId = req.pathVariable("accountId")
        validateUUID(accountId, "accountId")

        val request = req.awaitBody<List<CreateDreamRequest>>()
        val command = CreateDreamsCommand(
            dreams = request.map {
                CreateDreamsCommand.DreamCommand(
                    accountId = accountId,
                    title = it.title,
                    targetAmount = it.targetAmount,
                    deadline = it.deadline
                )
            }
        )
        try {
            createDreamsCommandHandler.execute(command)
            return ok().buildAndAwait()
        } catch (e: IllegalArgumentException) {
            return ServerResponse.badRequest().bodyValueAndAwait(mapOf("message" to e.message))
        }
    }

    suspend fun updateDream(req: ServerRequest): ServerResponse {
        val accountId = req.pathVariable("accountId")
        val dreamId = req.pathVariable("dreamId")

        validateUUID(accountId, "accountId")
        validateUUID(dreamId, "dreamId")

        val request = req.awaitBody<UpdateDreamRequest>()
        val command = UpdateDreamCommand(
            accountId = accountId,
            dreamId = dreamId,
            currentAmount = request.currentAmount
        )
        try {
            updateDreamCommandHandler.execute(command)
            return ok().buildAndAwait()
        } catch (e: IllegalArgumentException) {
            return ServerResponse.notFound().buildAndAwait()
        }
    }

    suspend fun getDreams(req: ServerRequest): ServerResponse {
        val accountId = req.pathVariable("accountId")
        // Check if dreamId path variable exists (it might not if route is just /accounts/{id}/dreams)
        val dreamId = try { req.pathVariable("dreamId") } catch (e: IllegalArgumentException) { null }

        try {
            validateUUID(accountId, "accountId")
            if (dreamId != null) validateUUID(dreamId, "dreamId")
        } catch (e: IllegalArgumentException) {
            return ServerResponse.badRequest().bodyValueAndAwait(e.message ?: "Invalid UUID")
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

    private fun validateUUID(id: String, fieldName: String) {
        try {
            UUID.fromString(id)
        } catch (e: Exception) {
            throw IllegalArgumentException("Invalid $fieldName format. Must be UUID.")
        }
    }
}

data class CreateDreamRequest(
    val title: String,
    val targetAmount: BigDecimal,
    val deadline: LocalDate
)

data class UpdateDreamRequest(
    val currentAmount: BigDecimal
)
