CREATE TABLE regulations (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    publication_date DATE,
    issuing_entity VARCHAR(50) NOT NULL,   
    category VARCHAR(50) NOT NULL,         
    status VARCHAR(50) NOT NULL DEFAULT 'ACTIVE',  
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE regulation_versions (
    id BIGSERIAL PRIMARY KEY,
    regulation_id BIGINT NOT NULL REFERENCES regulations(id),
    version_number INTEGER NOT NULL,
    file_key VARCHAR(500) NOT NULL,
    file_name VARCHAR(255) NOT NULL,
    file_size BIGINT,
    upload_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_regulation_versions_active 
    ON regulation_versions(regulation_id, active);