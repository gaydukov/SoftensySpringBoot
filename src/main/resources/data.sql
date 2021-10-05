INSERT INTO patient (id, date_of_birth, doctor_id, first_name, last_name, middle_name, phone_number)
VALUES (1, '2008-09-17', 1, 'Dima', 'Karlov', 'Vladimirivich', '0566541232');

INSERT INTO patient (id, date_of_birth, doctor_id, first_name, last_name, middle_name, phone_number)
VALUES (2, '2001-06-14', 2, 'Sasha', 'But', 'Olegovich', '0569513578');

INSERT INTO patient (id, date_of_birth, doctor_id, first_name, last_name, middle_name, phone_number)
VALUES (3, '1987-06-25', 3, 'Lena', 'Podolna', 'Viktorovna', '0567536987');

INSERT INTO doctor (id, date_of_birth, first_name, last_name, middle_name, phone_number, position)
VALUES (1, '1978-09-19', 'Ivan', 'Ivanov', 'Ivanovich', '0567894561', 'Terapevt');

INSERT INTO doctor (id, date_of_birth, first_name, last_name, middle_name, phone_number, position)
VALUES (2, '1986-07-11', 'Petr', 'Petrov', 'Petrovich', '0563214785', 'Hirurg');

INSERT INTO doctor (id, date_of_birth, first_name, last_name, middle_name, phone_number, position)
VALUES (3, '1993-07-08', 'Semen', 'Semenev', 'Semeneovich', '0563698521', 'Okulist');

INSERT INTO appointment (id, appointment_date, doctor_id, patient_id) VALUES (1, '2021-09-25 09:00:00.000000', 1, 1);
INSERT INTO appointment (id, appointment_date, doctor_id, patient_id) VALUES (2, '2021-09-25 09:30:00.000000', 1, 2);
INSERT INTO appointment (id, appointment_date, doctor_id, patient_id) VALUES (3, '2021-09-25 10:00:00.000000', 1, 3);
INSERT INTO appointment (id, appointment_date, doctor_id, patient_id) VALUES (4, '2021-09-25 09:00:00.000000', 2, 1);
INSERT INTO appointment (id, appointment_date, doctor_id, patient_id) VALUES (5, '2021-09-25 09:30:00.000000', 2, 2);
INSERT INTO appointment (id, appointment_date, doctor_id, patient_id) VALUES (6, '2021-09-25 10:00:00.000000', 2, 3);
INSERT INTO appointment (id, appointment_date, doctor_id, patient_id) VALUES (7, '2021-09-25 09:00:00.000000', 3, 1);
INSERT INTO appointment (id, appointment_date, doctor_id, patient_id) VALUES (8, '2021-09-25 09:30:00.000000', 3, 2);
INSERT INTO appointment (id, appointment_date, doctor_id, patient_id) VALUES (9, '2021-09-25 10:00:00.000000', 3, 3);

INSERT INTO user_security (user_id, login, password, role, account_type) VALUES
                                                                                   (1, 'patient1', '$2a$12$HiZKjxK2qkZGU/RnG1gP2O15dj/CSLIlELUNjI6QMsDR9x586HoPu', 'PATIENT', 'PATIENT');

INSERT INTO user_security (user_id, login, password, role, account_type) VALUES
    (2, 'patient2', '$2a$12$HiZKjxK2qkZGU/RnG1gP2O15dj/CSLIlELUNjI6QMsDR9x586HoPu', 'PATIENT', 'PATIENT');

INSERT INTO user_security (user_id, login, password, role, account_type) VALUES
    (3, 'patient3', '$2a$12$HiZKjxK2qkZGU/RnG1gP2O15dj/CSLIlELUNjI6QMsDR9x586HoPu', 'PATIENT', 'PATIENT');

INSERT INTO user_security (user_id, login, password, role, account_type) VALUES
    (1, 'doctor1', '$2a$12$bHUUHSqZ1t.6i8EtAK4BdesSfcNGRK1/LsgLFvIJhhGUX.eJW8GHW', 'ADMIN', 'DOCTOR');

INSERT INTO user_security (user_id, login, password, role, account_type) VALUES
    (2, 'doctor2', '$2a$12$bHUUHSqZ1t.6i8EtAK4BdesSfcNGRK1/LsgLFvIJhhGUX.eJW8GHW', 'DOCTOR', 'DOCTOR');

INSERT INTO user_security (user_id, login, password, role, account_type) VALUES
    (3, 'doctor3', '$2a$12$bHUUHSqZ1t.6i8EtAK4BdesSfcNGRK1/LsgLFvIJhhGUX.eJW8GHW', 'DOCTOR', 'DOCTOR');