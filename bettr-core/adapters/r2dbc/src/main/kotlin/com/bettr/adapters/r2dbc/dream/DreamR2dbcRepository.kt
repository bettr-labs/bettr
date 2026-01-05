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
                .bind("accountId", dream.accountId)
                .bind("title", dream.title)
                .bind("targetAmount", dream.targetAmount)
                .bind("currentAmount", dream.currentAmount)
                .bind("deadline", dream.deadline)
                .await()
        }
    }

    override suspend fun update(dream: Dream) {
        client.sql(DreamSqlExpressions.UPDATE)
            .bind("id", dream.id)
            .bind("targetAmount", dream.targetAmount)
            .bind("currentAmount", dream.currentAmount)
            .bind("deadline", dream.deadline)
            .await()
    }

    override suspend fun findAllByAccountId(accountId: String): List<Dream> {
        return client.sql(DreamSqlExpressions.FIND_ALL_BY_ACCOUNT_ID)
            .bind("accountId", accountId)
            .map { row, _ -> row.toDream() }
            .flow()
            .toList()
    }

    override suspend fun findByAccountIdAndDreamId(id: String, accountId: String): Dream? {
        return client.sql(DreamSqlExpressions.FIND_BY_ACCOUNT_ID_AND_DREAM_ID)
            .bind("id", id)
            .bind("accountId", accountId)
            .map { row, _ -> row.toDream() }
            .flow()
            .firstOrNull()
    }

    private fun Row.toDream() = Dream(
        id = this.get<String>("id"),
        accountId = this.get<String>("accountId"),
        title = this.get<String>("title"),
        targetAmount = this.get<BigDecimal>("targetAmount"),
        currentAmount = this.get<BigDecimal>("currentAmount"),
        deadline = this.get<LocalDate>("deadline")
    )
}