CREATE TABLE pets (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    owner_id INTEGER NOT NULL REFERENCES owners(id),
    veterinarian_id INTEGER REFERENCES veterinarians(id),
    birth_date DATE,
    gender VARCHAR NOT NULL,
    nickname VARCHAR NOT NULL  
);