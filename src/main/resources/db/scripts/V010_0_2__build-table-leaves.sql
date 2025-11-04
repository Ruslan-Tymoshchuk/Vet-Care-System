CREATE TABLE leaves (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    staff_id INTEGER NOT NULL REFERENCES staffs(id),
    begin_date DATE,
    complete_date DATE,
    leave_type VARCHAR NOT NULL  
);

CREATE INDEX idx_leaves_staff_begin_complete ON leaves (staff_id, begin_date, complete_date);