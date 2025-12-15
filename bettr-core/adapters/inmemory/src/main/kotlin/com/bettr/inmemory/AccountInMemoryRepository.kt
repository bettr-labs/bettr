package com.bettr.inmemory

import com.bettr.domain.Account
import com.bettr.domain.AccountRepository
import java.util.concurrent.ConcurrentHashMap

class AccountInMemoryRepository : AccountRepository {
    private val repository: MutableMap<String, Account> = ConcurrentHashMap()

    override fun create(account: Account) {
        repository[account.id] = account
    }

    override fun update(account: Account) {
        repository[account.id] = account
    }

    override fun findById(id: String): Account? {
        return repository[id]
    }

    override fun findByNickname(nickname: String): Account? {
        return repository.values.find { it.nickname == nickname }
    }
}