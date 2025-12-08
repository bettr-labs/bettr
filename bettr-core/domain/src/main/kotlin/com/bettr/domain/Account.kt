package com.bettr.domain

data class Account(
    val id: String,
    val nickname: String
) {

    companion object {
        fun enroll(id: String, nickname: String): Account {
            return Account(
                id = id,
                nickname = nickname
            ).validate()
        }
    }

    private fun validate(): Account {
        require(nickname.isNotBlank()) { "Nickname must not be blank." }
        return this
    }
}