CREATE TABLE IF NOT EXISTS bettr.dreams (
    id VARCHAR(36) PRIMARY KEY,
    account_id VARCHAR(36) NOT NULL,
    title VARCHAR(255) NOT NULL,
    target_amount NUMERIC(19,2) NOT NULL,
    current_amount NUMERIC(19,2) NOT NULL DEFAULT 0,
    deadline DATE NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_dream_account
        FOREIGN KEY (account_id) REFERENCES bettr.accounts(id)
        ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_dream_account_id
    ON bettr.dreams(account_id);