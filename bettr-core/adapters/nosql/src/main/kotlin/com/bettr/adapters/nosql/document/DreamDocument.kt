package com.bettr.adapters.nosql.document

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.time.LocalDate

@Document(collection = "dream")
@CompoundIndex(
    name = "account_dream_idx",
    def = "{'accountId': 1, 'id': 1}"
)
data class DreamDocument(
    @Id val id: String,
    @Indexed val accountId: String,
    val title: String,
    val targetAmount: BigDecimal,
    val currentAmount: BigDecimal,
    val deadline: LocalDate
)
