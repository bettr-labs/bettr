package com.bettr.application.dream

import com.bettr.domain.dream.DreamRepository

class UpdateDreamCommandHandler(
    private val dreamRepository: DreamRepository
) {
    fun execute(command: UpdateDreamCommand) {
        val dream = dreamRepository.findByAccountIdAndDreamId(command.accountId, command.dreamId)
            ?: throw IllegalArgumentException("Dream not found")

        val updatedDream = dream.copy(
            currentAmount = command.currentAmount
        )

        dreamRepository.update(updatedDream)
    }
}

