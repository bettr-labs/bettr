package com.bettr.adapters.nosql.document

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document("account")
data class AccountDocument(
    @Id val id: String,
    val nickname: String,
    val password: String,
    val status: String,
    val createdAt: Instant
)
