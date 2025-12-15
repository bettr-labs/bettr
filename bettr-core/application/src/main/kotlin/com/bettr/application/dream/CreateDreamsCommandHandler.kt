package com.bettr.application.dream

<<<<<<< HEAD
import com.bettr.domain.AccountRepository
=======
>>>>>>> ce578694087b92af98eff4e83594217caad986cd
import com.bettr.domain.dream.Dream
import com.bettr.domain.dream.DreamRepository
import java.math.BigDecimal
import java.util.UUID

class CreateDreamsCommandHandler(
<<<<<<< HEAD
    private val dreamRepository: DreamRepository,
    private val accountRepository: AccountRepository
) {
    fun execute(command: CreateDreamsCommand) {
        val dreams = command.dreams.map {
            if (accountRepository.findById(it.accountId) == null) {
                throw IllegalArgumentException("Account not found")
            }

=======
    private val dreamRepository: DreamRepository
) {
    fun execute(command: CreateDreamsCommand) {
        val dreams = command.dreams.map {
>>>>>>> ce578694087b92af98eff4e83594217caad986cd
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

