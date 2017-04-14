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

INSERT INTO users VALUES ('rick', '$2a$10$hm7WBFHrhsOU.jjjNPkM7OnGsh1uOPQmhuwtd5wI9w0zcQ/vGb2Z2');
INSERT INTO user_roles(username, role) VALUES ('rick', 'ROLE_ADMIN');

INSERT INTO users VALUES ('cman', '$2a$10$jWH7sGzKTReVhtpk1z5Wj.ZesC/FoN7oSrKyJlQpADKq9comQzn/.');
INSERT INTO user_roles(username, role) VALUES ('maria', 'ROLE_USER');

INSERT INTO users VALUES ('tommy', '$2a$10$BXxF/wzuGX8gY5Pv.AebTuto3etd7af8ocEmAsNB5QPot8G07CNdq');
INSERT INTO user_roles(username, role) VALUES ('tommy', 'ROLE_USER');

CREATE TABLE ticket (
    id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    name VARCHAR(255) NOT NULL,
    subject VARCHAR(255) NOT NULL,
    body VARCHAR(255) NOT NULL,
    type VARCHAR(255),
    postType VARCHAR(255),
    belongTo INTEGER,
    PRIMARY KEY (id)
);

CREATE TABLE attachment (
    id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    filename VARCHAR(255) DEFAULT NULL,
    content_type VARCHAR(255) DEFAULT NULL,
    content BLOB DEFAULT NULL,
    ticket_id INTEGER DEFAULT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (ticket_id) REFERENCES ticket(id) 
);

INSERT INTO ticket (name, subject, body, type, postType, belongTo) VALUES ('keith', 'hihi', 'hihi', 'lab', null , 0);
INSERT INTO ticket (name, subject, body, type, postType, belongTo) VALUES ('cman', 'What\'s the advantages of DAO?', 'I have a question as the subject mentioned.', 'lecture', null , 0);
INSERT INTO ticket (name, subject, body, type, postType, belongTo) VALUES ('rick', 'Tool for group project', 'We have done our group project through NetBean.', 'other', null , 0);
INSERT INTO ticket (name, subject, body, type, postType, belongTo) VALUES ('tommy', '', 'yes', 'reply', 'reply' , 3);
INSERT INTO ticket (name, subject, body, type, postType, belongTo) VALUES ('cman', '', 'hi keith', 'reply', 'reply' , 1);