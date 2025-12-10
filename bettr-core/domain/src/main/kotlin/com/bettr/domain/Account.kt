package com.bettr.domain

data class Account(
    val id: String,
    val nickname: String,
    val password: String
) {

    companion object {
        fun enroll(id: String, nickname: String, password: String): Account {
            return Account(
                id = id,
                nickname = nickname,
                password = password
            ).validate()
        }
    }

    private fun validate(): Account {
        require(nickname.isNotBlank()) { "Nickname must not be blank." }
        require(password.isNotBlank()) { "Password must not be blank." }
        return this
    }
}