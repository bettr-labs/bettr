package com.bettr.application.dream

import com.bettr.domain.AccountRepository
import com.bettr.domain.dream.Dream
import com.bettr.domain.dream.DreamRepository
import java.math.BigDecimal
import java.util.UUID

class CreateDreamsCommandHandler(
    private val dreamRepository: DreamRepository,
    private val accountRepository: AccountRepository
) {
    fun execute(command: CreateDreamsCommand) {
        val dreams = command.dreams.map {
            if (accountRepository.findById(it.accountId) == null) {
                throw IllegalArgumentException("Account not found")
            }

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

