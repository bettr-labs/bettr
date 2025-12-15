package com.bettr.application

import com.bettr.domain.AccountRepository

class UpdateAccountCommandHandler(
    private val accountRepository: AccountRepository
) {
    fun execute(command: UpdateAccountCommand) {
        val account = accountRepository.findById(command.accountId)
            ?: throw IllegalArgumentException("Account not found")

        val updatedAccount = account.copy(
            nickname = command.nickname ?: account.nickname
        )

        accountRepository.update(updatedAccount)
    }
}

