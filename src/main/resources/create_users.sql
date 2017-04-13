CREATE TABLE users (
    username VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    PRIMARY KEY (username)
);

CREATE TABLE user_roles (
    user_role_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    username VARCHAR(50) NOT NULL,
    role VARCHAR(50) NOT NULL,
    PRIMARY KEY (user_role_id),
    FOREIGN KEY (username) REFERENCES users(username)
);

INSERT INTO users (username, password) VALUES ('keith', '$2a$10$ZBEy.THkYX9Y9aHn1pxwv.7FyeGgUPuC5WuEr8v/N97.wewAysN1u');
INSERT INTO user_roles(username, role) VALUES ('keith', 'ROLE_USER');
INSERT INTO user_roles(username, role) VALUES ('keith', 'ROLE_ADMIN');

INSERT INTO users VALUES ('andrew', '$2a$10$8J1B9BZR0wDnVuMcIh9H2e5IYgiQo2VW/pYRJyEDf3.8v96RfathO');
INSERT INTO user_roles(username, role) VALUES ('andrew', 'ROLE_ADMIN');

INSERT INTO users VALUES ('maria', '$2a$10$uDnJYy.hZtHVNcCjtP5cGOISNBKBVx.hWq1Ji7k2kyu1oN3Cs1IOi');
INSERT INTO user_roles(username, role) VALUES ('maria', 'ROLE_USER');

INSERT INTO users VALUES ('oliver', '$2a$10$G.tp7.mgJQ6jHQTfLy3SlOt5wnLd3GSphcsrGAJi2DcZVLPhszixO');
INSERT INTO user_roles(username, role) VALUES ('oliver', 'ROLE_USER');