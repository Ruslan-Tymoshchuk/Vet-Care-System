INSERT INTO users (first_name, last_name, email, password, account_non_expired, account_non_locked, credentials_non_expired, enabled, dt_login) 
VALUES ('Emily', 'Smith', 'emily@gmail.com','$2a$12$d1XrgmOS0E30LrNWEjAOF.lKpkc/57LX7KqzXfH23UqzDK3DGdeBS', true, true, true, true, '2022-08-08 01:00:00'), 
       ('Dustin', 'Martinez', 'dustin@gmail.com', '$2a$12$L12LuqZWgvKxSnxWHEGdxOObFZevCxBg7tSUJ9VSpmk/KFl/dGnPq', true, true, true, true, '2022-08-08 02:00:00'), 
       ('Katie', 'Anderson', 'katie@gmail.com', '$2a$12$pU94kYmr60IoPMhdjvsab.4RG4oDjQLXalIdxyvBUoXxNiWins.8a', true, true, true, true, '2022-08-08 03:00:00'), 
       ('Emma', 'Taylor', 'emma@gmail.com', '$2a$10$fD.lmz9CqWzjA9XerPqtFOj/w3iucFuKMAYcIyz0tL1Nuj4tRCs72', true, true, true, true, '2022-08-08 04:00:00'), 
       ('Dominique', 'Moore', 'dominique@gmail.com', '$2a$10$yKQrDcmYs5y4.jpicX08BO0JUa/Ef9SmmDjUhDhckwXqLkR9WnIfS', true, true, true, true, '2022-08-08 05:00:00');
       
--password: test_password

INSERT INTO authorities (title)
VALUES ('USER'),
       ('ADMIN');
       
INSERT INTO user_authorities (user_id, authority_id)
VALUES (1, 1),
       (2, 1),
       (3, 1),
       (4, 1),
       (5, 1);
       
INSERT INTO animals (birth_date, gender, nickname, user_id) 
VALUES ('2018-07-10', 'FEMALE', 'Tagira', 1), 
       ('2019-07-10', 'FEMALE', 'TRIXIE', 2), 
       ('2019-07-10', 'MALE', 'Keks', 3), 
       ('2019-03-10', 'MALE', 'Sulin', 4), 
       ('2019-05-07', 'MALE', 'Chapye', 5);