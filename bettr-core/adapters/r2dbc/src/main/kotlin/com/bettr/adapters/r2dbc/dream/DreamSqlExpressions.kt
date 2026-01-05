package com.bettr.adapters.r2dbc.dream

object DreamSqlExpressions {

    const val CREATE = """
        INSERT INTO dreams (id, account_id, title, target_amount, current_amount, deadline)
        VALUES (:id, :account_id, :title, :target_amount, :current_amount, :deadline)
    """

    const val UPDATE = """
        UPDATE dreams
        SET target_amount = :target_amount,
            current_amount = :current_amount,
            deadline = :deadline
        WHERE id = :id
    """

    const val FIND_ALL_BY_ACCOUNT_ID = """
        SELECT id, account_id, title, target_amount, current_amount, deadline
        FROM dreams
        WHERE account_id = :account_id
    """

    const val FIND_BY_ACCOUNT_ID_AND_DREAM_ID = """
        SELECT id, account_id, title, target_amount, current_amount, deadline
        FROM dreams
        WHERE id = :id AND account_id = :account_id
    """
}