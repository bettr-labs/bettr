package com.bettr.application

import com.bettr.domain.AccountRepository

class LoginCommandHandler(
    private val accountRepository: AccountRepository
) {

    fun execute(command: LoginCommand) {
        val account = accountRepository.findByNickname(command.nickname)
            ?: throw IllegalArgumentException("Invalid credentials")

        if (account.password != command.password) {
            throw IllegalArgumentException("Invalid credentials")
        }
    }
}

