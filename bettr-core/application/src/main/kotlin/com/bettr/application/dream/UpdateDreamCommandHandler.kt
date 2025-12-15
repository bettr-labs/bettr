package com.bettr.application.dream

import com.bettr.domain.AccountRepository
import com.bettr.domain.AccountStatus
import com.bettr.domain.dream.DreamRepository

class UpdateDreamCommandHandler(
    private val dreamRepository: DreamRepository,
    private val accountRepository: AccountRepository
) {
    fun execute(command: UpdateDreamCommand) {
        val dream = dreamRepository.findByAccountIdAndDreamId(command.accountId, command.dreamId)
            ?: throw IllegalArgumentException("Dream not found")

        val account = accountRepository.findById(command.accountId)
            ?: throw IllegalArgumentException("Account not found")

        if (account.status != AccountStatus.ACTIVE) {
            throw IllegalArgumentException("Account is not active")
        }

        val updatedDream = dream.copy(
            currentAmount = command.currentAmount
        )

        dreamRepository.update(updatedDream)
    }
}

