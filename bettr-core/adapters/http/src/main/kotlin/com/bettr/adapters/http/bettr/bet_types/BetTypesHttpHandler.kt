package com.bettr.adapters.http.bettr.bet_types

import com.bettr.domain.dream.DreamRepository
import com.bettr.domain.bet_types.BetType
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.buildAndAwait
import java.math.BigDecimal
import java.time.LocalDate

class BetTypesHttpHandler() {
    suspend fun getBetTypes(req: ServerRequest): ServerResponse {
        val types = BetType.values().map {
            mapOf(
                "key" to it.name,
                "label" to it.label
            )
        }
        return ok().bodyValueAndAwait(types)
    }
}
