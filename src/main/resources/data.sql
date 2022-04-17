INSERT INTO roles (role_id, role_name, description)
VALUES (1, 'admin', 'Administrator'),
       (2, 'user', 'User');

INSERT INTO users (user_id, username, password, firstname, lastname, security_question, security_answer)
VALUES (1, 'admin', 'P@ssw0rd', 'Ihor', 'Dynka', 'test', 'test'),
       (2, 'admin', 'P@ssw0rd', 'Vira', 'Dynka', 'test', 'test'),
       (3, 'user', 'P@ssw0rd', 'Lydmula', 'Dynka', 'test', 'test'),
       (4, 'user', 'P@ssw0rd', 'Bohdan', 'Dynka', 'test', 'test');

INSERT INTO user_roles (id, user_id, role_id)
VALUES (1, 1, 1),
       (2, 2, 1),
       (3, 3, 2),
       (4, 4, 2);



