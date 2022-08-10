INSERT INTO users (name, password, account_non_locked, failed_attempt, lock_time) 
VALUES ('Oleh', '$2a$12$d1XrgmOS0E30LrNWEjAOF.lKpkc/57LX7KqzXfH23UqzDK3DGdeBS', true, 0, null), 
       ('Anatolii', '$2a$12$L12LuqZWgvKxSnxWHEGdxOObFZevCxBg7tSUJ9VSpmk/KFl/dGnPq', true, 1, null), 
       ('Olga', '$2a$12$pU94kYmr60IoPMhdjvsab.4RG4oDjQLXalIdxyvBUoXxNiWins.8a', true, 9, null), 
       ('Oksana', '$2a$10$fD.lmz9CqWzjA9XerPqtFOj/w3iucFuKMAYcIyz0tL1Nuj4tRCs72', true, 3, null), 
       ('Mikhail', '$2a$10$yKQrDcmYs5y4.jpicX08BO0JUa/Ef9SmmDjUhDhckwXqLkR9WnIfS', false, 10, '2022-08-08 00:00:00');
       
--password: test_password
       
INSERT INTO animals (birth_date, gender, nickname, person_id) 
VALUES ('2018-07-10', 'FEMALE', 'Tagira', 1), 
       ('2019-07-10', 'FEMALE', 'TRIXIE', 2), 
       ('2019-07-10', 'MALE', 'Keks', 3), 
       ('2019-03-10', 'MALE', 'Sulin', 4), 
       ('2019-05-07', 'MALE', 'Chapye', 5);