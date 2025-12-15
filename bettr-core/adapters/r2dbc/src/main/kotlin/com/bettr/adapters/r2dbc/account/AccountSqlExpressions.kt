package com.bettr.adapters.r2dbc.account

object AccountSqlExpressions {

    const val CREATE = """
        INSERT INTO accounts (id, nickname, password, created_at, status)
        VALUES (:id, :nickname, :password, :created_at, :status)
    """

    const val UPDATE = """
        UPDATE accounts
        SET nickname = :nickname,
            password = :password,
            status = :status
        WHERE id = :id
    """

    const val FIND_BY_ID = """
        SELECT id, nickname, password, created_at, status
        FROM accounts
        WHERE id = :id
    """

    const val FIND_BY_NICKNAME = """
        SELECT id, nickname, password, created_at, status
        FROM accounts
        WHERE nickname = :nickname
    """
}