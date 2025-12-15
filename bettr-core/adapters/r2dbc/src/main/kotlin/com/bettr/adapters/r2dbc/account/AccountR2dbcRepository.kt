package com.bettr.adapters.r2dbc.account

import com.bettr.adapters.r2dbc.get
import com.bettr.domain.Account
import com.bettr.domain.AccountRepository
import com.bettr.domain.AccountStatus
import io.r2dbc.spi.Row
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.datetime.toJavaInstant
import kotlinx.datetime.toKotlinInstant
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.await
import org.springframework.r2dbc.core.flow
import java.time.Instant

class AccountR2dbcRepository(
    private val client: DatabaseClient
) : AccountRepository {

    override suspend fun create(account: Account) {
        client.sql(AccountSqlExpressions.CREATE)
            .bind("id", account.id)
            .bind("nickname", account.nickname)
            .bind("password", account.password)
            .bind("created_at", account.createdAt.toJavaInstant())
            .bind("status", account.status.name)
            .await()
    }

    override suspend fun update(account: Account) {
        client.sql(AccountSqlExpressions.UPDATE)
            .bind("id", account.id)
            .bind("nickname", account.nickname)
            .bind("password", account.password)
            .bind("status", account.status.name)
            .await()
    }

    override suspend fun findById(id: String): Account? {
        return client.sql(AccountSqlExpressions.FIND_BY_ID)
            .bind("id", id)
            .map { row, _ -> row.toAccount() }
            .flow()
            .firstOrNull()
    }

    override suspend fun findByNickname(nickname: String): Account? {
        return client.sql(AccountSqlExpressions.FIND_BY_NICKNAME)
            .bind("nickname", nickname)
            .map { row, _ -> row.toAccount() }
            .flow()
            .firstOrNull()
    }

    private fun Row.toAccount() = Account(
        id = this.get<String>("id"),
        nickname = this.get<String>("nickname"),
        password = this.get<String>("password"),
        createdAt = this.get<Instant>("created_at").toKotlinInstant(),
        status = AccountStatus.valueOf(this.get<String>("status"))
    )
}