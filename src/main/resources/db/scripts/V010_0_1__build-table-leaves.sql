CREATE TABLE leaves (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    staff_id INTEGER NOT NULL REFERENCES staffs(id),
    begin_date DATE,
    complete_date DATE,
    leave_type VARCHAR NOT NULL  
);