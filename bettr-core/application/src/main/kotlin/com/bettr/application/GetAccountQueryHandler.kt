package com.bettr.application

import com.bettr.domain.Account
import com.bettr.domain.AccountRepository

class GetAccountQueryHandler(
    private val accountRepository: AccountRepository
) {
    fun execute(accountId: String): Account? {
        return accountRepository.findById(accountId)
    }
}

