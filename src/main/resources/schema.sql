CREATE TABLE users
(
    user_id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    username          VARCHAR(255) NOT NULL,
    password          VARCHAR(255) NOT NULL,
    firstname         VARCHAR(255) NOT NULL,
    lastname          VARCHAR(255) NOT NULL,
    security_question VARCHAR(255) NOT NULL,
    security_answer   VARCHAR(255) NOT NULL
);

CREATE TABLE roles
(
    role_id     BIGINT AUTO_INCREMENT PRIMARY KEY,
    role_name   VARCHAR(100) NOT NULL,
    description VARCHAR(100) NOT NULL
);

CREATE TABLE user_roles
(
    id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    CONSTRAINT FK_USER_ID FOREIGN KEY (user_id) REFERENCES users (user_id),
    CONSTRAINT FK_ROLE_ID FOREIGN KEY (role_id) REFERENCES roles (role_id)
);