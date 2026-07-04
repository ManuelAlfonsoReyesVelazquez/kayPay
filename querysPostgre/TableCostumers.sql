CREATE TABLE customers(

    id BIGSERIAL PRIMARY KEY,

    document_type VARCHAR(20) NOT NULL,

    document_number VARCHAR(50) NOT NULL UNIQUE,

    name VARCHAR(200),

    email VARCHAR(200),

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP

);