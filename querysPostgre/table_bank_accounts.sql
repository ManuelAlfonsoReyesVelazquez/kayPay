CREATE TABLE bank_accounts(

    id BIGSERIAL PRIMARY KEY,

    customer_id BIGINT NOT NULL,

    bank_code VARCHAR(10) NOT NULL,

    account_type VARCHAR(20) NOT NULL,

    account_number VARCHAR(50) NOT NULL,

    status VARCHAR(20) NOT NULL,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_account_customer
    FOREIGN KEY(customer_id)
    REFERENCES customers(id)

);