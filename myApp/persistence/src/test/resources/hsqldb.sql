SET DATABASE SQL SYNTAX PGS TRUE;

CREATE TABLE IF NOT EXISTS users
(
    userId      SERIAL PRIMARY KEY,
    name        varchar(100),
    password    varchar(100),
    mail        varchar(100) UNIQUE ,
    description varchar(256) default '',
    schedule    varchar(256) default ''
);

CREATE TABLE IF NOT EXISTS rating
(
    teacherId INTEGER,
    userId INTEGER NOT NULL,
    rate float check (rate <= 5),
    review varchar(256),
    FOREIGN KEY (userId) REFERENCES users ON DELETE CASCADE,
    FOREIGN KEY (teacherId) REFERENCES users ON DELETE CASCADE,
    PRIMARY KEY (teacherId,userId)
);

CREATE TABLE IF NOT EXISTS favourites
(
    teacherId INTEGER,
    studentId INTEGER NOT NULL,
    FOREIGN KEY (studentId) REFERENCES users ON DELETE CASCADE,
    FOREIGN KEY (teacherId) REFERENCES users ON DELETE CASCADE,
    PRIMARY KEY (teacherId,studentId)
);

CREATE TABLE IF NOT EXISTS images
(
    userId INTEGER,
    image  binary,
    FOREIGN KEY (userId) REFERENCES users ON DELETE CASCADE,
    PRIMARY KEY (userId)
);

CREATE TABLE IF NOT EXISTS subject
(
    subjectId SERIAL PRIMARY KEY,
    name      varchar(100)
);

CREATE TABLE IF NOT EXISTS teaches
(
    userId    INTEGER,
    subjectId INTEGER,
    price     INTEGER,
    level     INTEGER,
    FOREIGN KEY (userId) REFERENCES users ON DELETE CASCADE,
    FOREIGN KEY (subjectId) REFERENCES subject ON DELETE CASCADE,
    PRIMARY KEY (userId, subjectId)
);

CREATE TABLE IF NOT EXISTS classes
(
    classId   SERIAL PRIMARY KEY,
    studentId INTEGER NOT NULL,
    teacherId INTEGER NOT NULL,
    level     INTEGER NOT NULL,
    subjectId INTEGER NOT NULL,
    price     INTEGER NOT NULL,
    status    INTEGER NOT NULL,
    deleted INTEGER default 0 NOT NULL,
    request varchar(256) default '',
    reply    varchar(256) default '',
    FOREIGN KEY (studentId) REFERENCES users ON DELETE CASCADE,
    FOREIGN KEY (teacherId) REFERENCES users ON DELETE CASCADE,
    FOREIGN KEY (subjectId) REFERENCES subject ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS roles
(
    roleId SERIAL PRIMARY KEY,
    role varchar(100)
);

CREATE TABLE IF NOT EXISTS userRoles
(
    roleId INTEGER NOT NULL,
    userId INTEGER NOT NULL,
    PRIMARY KEY (roleId, userId),
    FOREIGN KEY (roleId) REFERENCES roles ON DELETE CASCADE,
    FOREIGN KEY (userId) REFERENCES users ON DELETE CASCADE
);

insert into roles values (0,'USER_TEACHER'),(1,'USER_STUDENT');
