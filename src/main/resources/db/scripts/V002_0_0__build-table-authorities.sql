CREATE TABLE authorities (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title VARCHAR NOT NULL
);

CREATE TABLE user_authorities (
    user_id INTEGER NOT NULL REFERENCES users(id),
    authority_id INTEGER NOT NULL REFERENCES authorities(id),
    UNIQUE(user_id, authority_id)
);