package com.bettr.adapters.r2dbc.dream

object DreamSqlExpressions {

    const val CREATE = """
        INSERT INTO dreams (id, accountId, title, targetAmount, currentAmount, deadline)
        VALUES (:id, :accountId, :title, :targetAmount, :currentAmount, :deadline)
    """

    const val UPDATE = """
        UPDATE dreams
        SET targetAmount = :targetAmount,
            currentAmount = :currentAmount,
            deadline = :deadline
        WHERE id = :id
    """

    const val FIND_ALL_BY_ACCOUNT_ID = """
        SELECT id, accountId, title, targetAmount, currentAmount, deadline
        FROM dreams
        WHERE accountId = :accountId
    """

    const val FIND_BY_ACCOUNT_ID_AND_DREAM_ID = """
        SELECT id, accountId, title, targetAmount, currentAmount, deadline
        FROM dreams
        WHERE id = :id AND accountId = :accountId
    """
}