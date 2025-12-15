package com.bettr.adapters.r2dbc

import io.r2dbc.spi.Row
import org.springframework.r2dbc.core.DatabaseClient

@Suppress("EXTENSION_SHADOWED_BY_MEMBER")
inline fun <reified T> Row.get(identifier: String): T = this.get(identifier, T::class.java)!!

inline fun <reified T> Row.getOrNull(identifier: String): T? = this.get(identifier, T::class.java)

inline fun <reified T> DatabaseClient.GenericExecuteSpec.bindOrNull(
    name: String,
    value: T?,
) = value?.let { this.bind(name, it as Any) } ?: this.bindNull(name, T::class.java)

inline fun <reified T> DatabaseClient.GenericExecuteSpec.bindIfNotNull(
    name: String,
    value: T?,
) = value?.let { this.bind(name, it as Any) } ?: this

fun <T> String.where(
    sql: String,
    value: T?,
) = this.trimEnd() + " " + (value?.let { sql } ?: "")

fun <T> String.where(
    sql: String,
    value: Iterable<T>,
) = this.trimEnd() + " " + if (value.count() != 0) (value.let { sql }) else ""

fun String.where(sql: String?) = sql?.let { "${this.trimEnd()} $sql" } ?: this
