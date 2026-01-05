package com.bettr.domain.dream

interface DreamRepository {
    suspend fun saveAll(dreams: List<Dream>)
    suspend fun update(dream: Dream): Boolean
    suspend fun findAllByAccountId(accountId: String): List<Dream>
    suspend fun findByAccountIdAndDreamId(accountId: String, dreamId: String): Dream?
}
