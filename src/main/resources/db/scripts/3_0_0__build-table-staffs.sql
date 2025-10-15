CREATE TABLE staffs (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES users(id),
    total_months_of_experience INTEGER NOT NULL,
    education_level VARCHAR NOT NULL
);