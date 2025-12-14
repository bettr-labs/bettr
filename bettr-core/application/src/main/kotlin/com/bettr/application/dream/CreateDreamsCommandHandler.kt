package com.bettr.application.dream

import com.bettr.domain.dream.Dream
import com.bettr.domain.dream.DreamRepository
import java.math.BigDecimal
import java.util.UUID

class CreateDreamsCommandHandler(
    private val dreamRepository: DreamRepository
) {
    fun execute(command: CreateDreamsCommand) {
        val dreams = command.dreams.map {
            Dream(
                dreamId = UUID.randomUUID().toString(),
                accountId = it.accountId,
                title = it.title,
                targetAmount = it.targetAmount,
                currentAmount = BigDecimal.ZERO,
                deadline = it.deadline
            )
        }
        dreamRepository.saveAll(dreams)
    }
}

