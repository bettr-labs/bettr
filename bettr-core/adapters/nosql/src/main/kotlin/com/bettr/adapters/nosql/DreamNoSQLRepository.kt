package com.bettr.adapters.nosql

import com.bettr.adapters.nosql.document.DreamDocument
import com.bettr.adapters.nosql.mapper.toDocument
import com.bettr.adapters.nosql.mapper.toDomain
import com.bettr.domain.dream.Dream
import com.bettr.domain.dream.DreamRepository
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query

class DreamNoSQLRepository(
    private val mongoTemplate: ReactiveMongoTemplate
) : DreamRepository {

    override suspend fun saveAll(dreams: List<Dream>) {
        val documents = dreams.map { it.toDocument() }

        mongoTemplate
            .insertAll(documents)
            .collectList()
            .awaitSingle()
    }

    override suspend fun update(dream: Dream) {
        mongoTemplate
            .save(dream.toDocument())
            .awaitSingle()
    }

    override suspend fun findByAccountId(accountId: String): List<Dream> {
        val query = Query(
            Criteria.where("accountId").`is`(accountId)
        )

        return mongoTemplate
            .find(query, DreamDocument::class.java)
            .map { it.toDomain() }
            .collectList()
            .awaitSingle()
    }

    override suspend fun findByAccountIdAndDreamId(
        accountId: String,
        id: String
    ): Dream? {
        val query = Query(
            Criteria.where("accountId").`is`(accountId)
                .and("_id").`is`(id)
        )

        return mongoTemplate
            .findOne(query, DreamDocument::class.java)
            .awaitSingleOrNull()
            ?.toDomain()
    }
}
