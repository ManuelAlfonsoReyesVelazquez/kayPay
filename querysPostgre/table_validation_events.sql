CREATE TABLE validation_events(

    id BIGSERIAL PRIMARY KEY,

    validation_id BIGINT NOT NULL,

    event_type VARCHAR(50) NOT NULL,

    payload JSONB,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_event_validation
    FOREIGN KEY(validation_id)
    REFERENCES validations(id)

);