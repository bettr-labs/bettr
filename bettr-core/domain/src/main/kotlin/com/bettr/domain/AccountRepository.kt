package com.bettr.domain

interface AccountRepository {
    suspend fun create(account: Account)
    suspend fun update(account: Account)
    suspend fun findById(id: String): Account?
    suspend fun findByNickname(nickname: String): Account?
}