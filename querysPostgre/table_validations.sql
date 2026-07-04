CREATE TABLE validations(

    id BIGSERIAL PRIMARY KEY,

    account_id BIGINT NOT NULL,

    request_id UUID NOT NULL,

    request_hash VARCHAR(64) NOT NULL,

    status VARCHAR(20) NOT NULL,

    ach_reference VARCHAR(100),

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_validation_account
    FOREIGN KEY(account_id)
    REFERENCES bank_accounts(id)

);