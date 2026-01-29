package com.bettr.adapters.nosql.mapper

import com.bettr.adapters.nosql.document.DreamDocument
import com.bettr.domain.dream.Dream
import java.time.LocalDate

fun Dream.toDocument(): DreamDocument =
    DreamDocument(
        id = this.id,
        accountId = this.accountId,
        title = this.title,
        targetAmount = this.targetAmount,
        currentAmount = this.currentAmount,
        deadline = this.deadline
    )

fun DreamDocument.toDomain(): Dream =
    Dream(
        id = this.id,
        accountId = this.accountId,
        title = this.title,
        targetAmount = this.targetAmount,
        currentAmount = this.currentAmount,
        deadline = this.deadline
    )
