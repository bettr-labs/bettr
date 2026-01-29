package com.bettr.domain.dream

interface DreamRepository {
    suspend fun saveAll(dreams: List<Dream>)
    suspend fun update(dream: Dream)
    suspend fun findByAccountId(accountId: String): List<Dream>
    suspend fun findByAccountIdAndDreamId(
        accountId: String,
        id: String
    ): Dream?
}
