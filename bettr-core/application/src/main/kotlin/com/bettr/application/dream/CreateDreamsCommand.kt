package com.bettr.application.dream

import java.math.BigDecimal
import java.time.LocalDate

data class CreateDreamsCommand(
    val dreams: List<DreamCommand>
) {
    data class DreamCommand(
        val accountId: String,
        val title: String,
        val targetAmount: BigDecimal,
        val deadline: LocalDate
    )
}

