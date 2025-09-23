CREATE TABLE veterinarians (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id INTEGER REFERENCES users(id),
    total_months_of_experience INTEGER NOT NULL,
    seniority_level VARCHAR NOT NULL
);