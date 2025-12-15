package com.bettr.application

import com.bettr.domain.AccountRepository
import com.bettr.domain.AccountStatus

class LoginCommandHandler(
    private val accountRepository: AccountRepository
) {

    fun execute(command: LoginCommand): String {
        val account = accountRepository.findByNickname(command.nickname)
            ?: throw IllegalArgumentException("combination of Nickname and password is invalid")

        if (account.password != command.password) {
            throw IllegalArgumentException("combination of Nickname and password is invalid")
        }

        if (account.status == AccountStatus.INACTIVE) {
            throw IllegalArgumentException("account is inactive")
        }

        return account.id
    }
}

