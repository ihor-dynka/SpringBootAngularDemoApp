INSERT INTO roles (role_name, description)
VALUES ('admin', 'Administrator'),
       ('user', 'User'),
       ('guest', 'Guest');

INSERT INTO users (username, password, email, firstname, lastname, security_question, security_answer)
VALUES ('admin', 'P@ssw0rd', 'defaultadmin@gmail.com', 'Default', 'Admin', 'test', 'test'),
       ('user', 'P@ssw0rd', 'defaultuser@gmail.com', 'Default', 'User', 'test', 'test');

INSERT INTO user_roles (user_id, role_id)
VALUES (1, 1),
       (2, 2);