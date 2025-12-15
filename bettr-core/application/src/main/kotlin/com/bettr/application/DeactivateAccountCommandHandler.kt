package com.bettr.application

import com.bettr.domain.AccountRepository
import com.bettr.domain.AccountStatus

class DeactivateAccountCommandHandler(
    private val accountRepository: AccountRepository
) {
    fun execute(accountId: String) {
        val account = accountRepository.findById(accountId)
            ?: throw IllegalArgumentException("Account not found")

        val updatedAccount = account.copy(
            status = AccountStatus.INACTIVE
        )

        accountRepository.update(updatedAccount)
    }
}

