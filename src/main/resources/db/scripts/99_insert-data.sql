INSERT INTO users (first_name, last_name, email, password, legal_certificate_id, account_non_expired, account_non_locked, credentials_non_expired, enabled, last_login) 
VALUES ('Emily', 'Smith', 'emily@gmail.com','$2a$12$d1XrgmOS0E30LrNWEjAOF.lKpkc/57LX7KqzXfH23UqzDK3DGdeBS', 45785589435212, true, true, true, true, '2022-08-08 01:00:00'), 
       ('Dustin', 'Martinez', 'dustin@gmail.com', '$2a$12$L12LuqZWgvKxSnxWHEGdxOObFZevCxBg7tSUJ9VSpmk/KFl/dGnPq', 45785582225212,true, true, true, true, '2022-08-08 02:00:00'), 
       ('Katie', 'Anderson', 'katie@gmail.com', '$2a$12$pU94kYmr60IoPMhdjvsab.4RG4oDjQLXalIdxyvBUoXxNiWins.8a', 45785589435222, true, true, true, true, '2022-08-08 03:00:00'), 
       ('Emma', 'Taylor', 'emma@gmail.com', '$2a$10$fD.lmz9CqWzjA9XerPqtFOj/w3iucFuKMAYcIyz0tL1Nuj4tRCs72', 45785589435888, true, true, true, true, '2022-08-08 04:00:00'), 
       ('Dominique', 'Moore', 'dominique@gmail.com', '$2a$10$yKQrDcmYs5y4.jpicX08BO0JUa/Ef9SmmDjUhDhckwXqLkR9WnIfS', 45785580005212, true, true, true, true, '2022-08-08 05:00:00');
       
--password: test_password

INSERT INTO authorities (title)
VALUES ('MANAGER'),
       ('OWNER'),
       ('VETERINARIAN');
       
INSERT INTO managers (user_id) VALUES (3), (4), (5);       
       
INSERT INTO owners (user_id) VALUES (1);

INSERT INTO veterinarians (user_id) VALUES (2);
       
INSERT INTO user_authorities (user_id, authority_id)
VALUES (1, 2),
       (2, 3),
       (3, 1),
       (4, 1),
       (5, 1);
       
INSERT INTO pets (owner_id, veterinarian_id, birth_date, gender, nickname) 
VALUES (1, 1, '2018-07-10', 'FEMALE', 'Tagira'), 
       (1, 1, '2019-07-10', 'FEMALE', 'TRIXIE'), 
       (1, 1, '2019-07-10', 'MALE', 'Keks'), 
       (1, 1, '2019-03-10', 'MALE', 'Sulin'), 
       (1, 1, '2019-05-07', 'MALE', 'Chapye');
       
INSERT INTO appointment_time_slots (start_time, end_time)
VALUES ('08:00:00', '08:20:00'),
       ('08:25:00', '08:45:00'),
       ('08:50:00', '09:10:00'),
       ('09:15:00', '09:35:00'),
       ('10:00:00', '10:20:00'),
       ('10:25:00', '10:45:00'),
       ('10:50:00', '11:10:00'),
       ('11:15:00', '11:35:00'),
       ('11:40:00', '12:00:00'),
       ('13:00:00', '13:20:00'),
       ('13:25:00', '13:45:00'),
       ('14:05:00', '14:25:00'),
       ('14:30:00', '14:50:00'),
       ('14:55:00', '15:15:00'),
       ('15:20:00', '15:40:00'),
       ('15:45:00', '16:05:00'),
       ('16:10:00', '16:30:00'),
       ('16:35:00', '16:55:00');