package com.bettr.adapters.r2dbc.dream

import com.bettr.adapters.r2dbc.get
import com.bettr.domain.dream.Dream
import com.bettr.domain.dream.DreamRepository
import io.r2dbc.spi.Row
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.datetime.toJavaInstant
import kotlinx.datetime.toKotlinInstant
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.await
import org.springframework.r2dbc.core.flow
import kotlinx.coroutines.reactor.awaitSingle

import java.time.Instant
import java.math.BigDecimal
import java.time.LocalDate
import kotlinx.coroutines.flow.toList



class DreamR2dbcRepository(
    private val client: DatabaseClient
) : DreamRepository {

    override suspend fun saveAll(dreams: List<Dream>) {
        dreams.forEach {
            dream -> 
            client.sql(DreamSqlExpressions.CREATE)
                .bind("id", dream.id)
                .bind("account_id", dream.accountId)
                .bind("title", dream.title)
                .bind("target_amount", dream.targetAmount)
                .bind("current_amount", dream.currentAmount)
                .bind("deadline", dream.deadline)
                .await()
        }
    }

    override suspend fun update(dream: Dream): Boolean {
        val rowsUpdated = client.sql(DreamSqlExpressions.UPDATE)
            .bind("id", dream.id)
            .bind("target_amount", dream.targetAmount)
            .bind("current_amount", dream.currentAmount)
            .bind("deadline", dream.deadline)
            .fetch()
            .rowsUpdated()
            .awaitSingle()
    
        return rowsUpdated > 0
    }

    override suspend fun findAllByAccountId(accountId: String): List<Dream> {
        return client.sql(DreamSqlExpressions.FIND_ALL_BY_ACCOUNT_ID)
            .bind("account_id", accountId)
            .map { row, _ -> row.toDream() }
            .flow()
            .toList()
    }

    override suspend fun findByAccountIdAndDreamId(id: String, accountId: String): Dream? {
        return client.sql(DreamSqlExpressions.FIND_BY_ACCOUNT_ID_AND_DREAM_ID)
            .bind("id", id)
            .bind("account_id", accountId)
            .map { row, _ -> row.toDream() }
            .flow()
            .firstOrNull()
    }

    private fun Row.toDream() = Dream(
        id = this.get<String>("id"),
        accountId = this.get<String>("account_id"),
        title = this.get<String>("title"),
        targetAmount = this.get<BigDecimal>("target_amount"),
        currentAmount = this.get<BigDecimal>("current_amount"),
        deadline = this.get<LocalDate>("deadline")
    )
}