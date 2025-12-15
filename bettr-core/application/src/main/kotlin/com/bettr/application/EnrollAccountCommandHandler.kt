package com.bettr.application

import com.bettr.domain.Account
import com.bettr.domain.AccountRepository

data class EnrollAccountCommandHandler(
    private val accountRepository: AccountRepository
) {

    fun execute(command: EnrollAccountCommand): String {
        if (accountRepository.findByNickname(command.nickname) != null) {
            throw IllegalArgumentException("Nickname already in use")
        }

        val account = Account.enroll(
            id = command.aggregateId,
            nickname = command.nickname,
            password = command.password
        )

        accountRepository.create(account)
        return account.id
    }
}