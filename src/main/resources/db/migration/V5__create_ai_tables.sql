CREATE EXTENSION IF NOT EXISTS vector;

CREATE TABLE consultations (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    question TEXT NOT NULL,
    answer TEXT,
    feedback BOOLEAN,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE consultation_sources (
    consultation_id BIGINT NOT NULL,
    document_title VARCHAR(255),
    page_number INTEGER,
    excerpt TEXT,
    FOREIGN KEY (consultation_id) REFERENCES consultations(id) ON DELETE CASCADE
);

CREATE TABLE regulation_embeddings (
    embedding_id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    text TEXT,
    metadata JSONB,
    embedding vector(768)
);