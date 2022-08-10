CREATE TABLE users (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR NOT NULL UNIQUE,
    password VARCHAR NOT NULL,
    account_non_locked BOOLEAN DEFAULT true,
    failed_attempt INTEGER NOT NULL DEFAULT 0,
    lock_time TIMESTAMP
);

CREATE TABLE animals (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    birth_date DATE,
    gender VARCHAR NOT NULL,
    nickname VARCHAR NOT NULL UNIQUE,
    person_id INTEGER REFERENCES users(id) ON DELETE SET NULL
);