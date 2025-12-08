package com.bettr.application

import com.bettr.domain.Account
import com.bettr.domain.AccountRepository

data class EnrollAccountCommandHandler(
    private val accountRepository: AccountRepository
) {

    fun execute(command: EnrollAccountCommand) {
        val account = Account.enroll(
            id = command.aggregateId,
            nickname = command.nickname
        )

        accountRepository.create(account)
    }
}