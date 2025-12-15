package com.bettr.inmemory

import com.bettr.domain.dream.Dream
import com.bettr.domain.dream.DreamRepository

class InMemoryDreamRepository : DreamRepository {
    private val dreams = mutableListOf<Dream>()

    override fun saveAll(dreams: List<Dream>) {
        this.dreams.addAll(dreams)
    }

    override fun update(dream: Dream) {
        val index = dreams.indexOfFirst { it.dreamId == dream.dreamId && it.accountId == dream.accountId }
        if (index != -1) {
            dreams[index] = dream
        }
    }

    override fun findByAccountId(accountId: String): List<Dream> {
        return dreams.filter { it.accountId == accountId }
    }

    override fun findByAccountIdAndDreamId(accountId: String, dreamId: String): Dream? {
        return dreams.find { it.accountId == accountId && it.dreamId == dreamId }
    }
}
