package com.bettr.application.dream

import java.math.BigDecimal

data class UpdateDreamCommand(
    val accountId: String,
    val dreamId: String,
    val currentAmount: BigDecimal
)

