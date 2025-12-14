package com.bettr.domain.dream

interface DreamRepository {
    fun saveAll(dreams: List<Dream>)
    fun update(dream: Dream)
    fun findByAccountId(accountId: String): List<Dream>
    fun findByAccountIdAndDreamId(accountId: String, dreamId: String): Dream?
}

