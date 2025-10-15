CREATE TABLE leaves (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    begin_date DATE,
    complete_date DATE,
    leave_type VARCHAR NOT NULL  
);