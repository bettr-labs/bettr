package com.bettr.domain.dream

import java.math.BigDecimal
import java.time.LocalDate

data class Dream(
    val id: String,
    val accountId: String,
    val title: String,
    val targetAmount: BigDecimal,
    val currentAmount: BigDecimal,
    val deadline: LocalDate
)