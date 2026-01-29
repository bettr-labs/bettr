package com.bettr.adapters.nosql.mapper

import com.bettr.adapters.nosql.document.AccountDocument
import com.bettr.domain.Account
import com.bettr.domain.AccountStatus
import kotlinx.datetime.toKotlinInstant
import kotlinx.datetime.toJavaInstant

fun AccountDocument.toDomain(): Account =
    Account(
        id = id,
        nickname = nickname,
        password = password,
        status = AccountStatus.valueOf(status),
        createdAt = createdAt.toKotlinInstant()
    )

fun Account.toDocument(): AccountDocument =
    AccountDocument(
        id = id,
        nickname = nickname,
        password = password,
        status = status.name,
        createdAt = createdAt.toJavaInstant()
    )
