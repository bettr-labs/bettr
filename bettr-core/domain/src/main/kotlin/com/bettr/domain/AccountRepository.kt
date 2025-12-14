package com.bettr.domain

interface AccountRepository {
    fun create(account: Account)
    fun update(account: Account)
    fun findById(id: String): Account?
    fun findByNickname(nickname: String): Account?
}