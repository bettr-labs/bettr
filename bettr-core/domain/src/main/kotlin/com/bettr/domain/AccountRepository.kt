package com.bettr.domain

interface AccountRepository {
    fun create(account: Account)
}