package com.bettr.adapters.nosql

import com.bettr.adapters.nosql.document.AccountDocument
import com.bettr.adapters.nosql.mapper.toDocument
import com.bettr.adapters.nosql.mapper.toDomain
import com.bettr.domain.Account
import com.bettr.domain.AccountRepository
import com.bettr.domain.AccountStatus
import kotlinx.datetime.toJavaInstant
import kotlinx.datetime.toKotlinInstant
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import kotlinx.coroutines.reactor.awaitSingleOrNull

class AccountNoSQLRepository(
    private val template: ReactiveMongoTemplate
) : AccountRepository {

    override suspend fun create(account: Account) {
        template.insert(account.toDocument()).awaitSingleOrNull()
    }

    override suspend fun findById(id: String): Account? =
        template
            .findById(id, AccountDocument::class.java)
            .awaitSingleOrNull()
            ?.toDomain()

    override suspend fun findByNickname(nickname: String): Account? =
        template
            .findOne(
                Query.query(Criteria.where("nickname").`is`(nickname)),
                AccountDocument::class.java
            )
            .awaitSingleOrNull()
            ?.toDomain()

    override suspend fun update(account: Account) {
        template.save(account.toDocument()).awaitSingleOrNull()
    }
}