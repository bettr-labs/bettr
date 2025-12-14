package com.bettr.domain

import java.time.Instant

enum class AccountStatus {
    ACTIVE,
    INACTIVE
}

data class Account(
    val id: String,
    val nickname: String,
    val password: String,
    val createdAt: Instant,
    val status: AccountStatus
) {

    companion object {
        fun enroll(id: String, nickname: String, password: String): Account {
            return Account(
                id = id,
                nickname = nickname,
                password = password,
                createdAt = Instant.now(),
                status = AccountStatus.ACTIVE
            ).validate()
        }
    }

    private fun validate(): Account {
        require(nickname.isNotBlank()) { "Nickname must not be blank." }
        require(password.isNotBlank()) { "Password must not be blank." }
        return this
    }
}