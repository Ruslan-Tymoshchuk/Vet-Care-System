CREATE TABLE appointment_time_slots (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL
);