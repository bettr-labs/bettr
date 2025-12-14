package com.bettr.application

data class UpdateAccountCommand(
    val accountId: String,
    val nickname: String? = null
)

