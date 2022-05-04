INSERT INTO roles (role_name, description)
VALUES ('admin', 'Administrator'),
       ('user', 'User'),
       ('guest', 'Guest');

INSERT INTO users (username, password, email, firstname, lastname, security_question, security_answer)
VALUES ('admin', '$2a$12$qu7wPJ4eZD7x05w7O/bqfuBzOv.vW6a5ol8c9IiLIgeruBoyRsZZa', 'defaultadmin@gmail.com', 'Default', 'Admin', 'test', 'test'),
       ('user', '$2a$12$qu7wPJ4eZD7x05w7O/bqfuBzOv.vW6a5ol8c9IiLIgeruBoyRsZZa', 'defaultuser@gmail.com', 'Default', 'User', 'test', 'test');

INSERT INTO user_roles (user_id, role_id)
VALUES (1, 1),
       (2, 2);