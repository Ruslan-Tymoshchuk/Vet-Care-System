CREATE TABLE appointments (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    veterinarian_id INTEGER NOT NULL REFERENCES veterinarians(id),
    owner_id INTEGER NOT NULL REFERENCES owners(id),
    pet_id INTEGER NOT NULL REFERENCES pets(id),
    visit_date DATE,
    visit_time TIME,
    room VARCHAR NOT NULL,
    type_of_visit VARCHAR NOT NULL,
    status VARCHAR NOT NULL
);