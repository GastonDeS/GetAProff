SET DATABASE SQL SYNTAX PGS TRUE;
CREATE TYPE BYTEA AS VARBINARY(32000000);

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
    PRIMARY KEY (userId, subjectId, level)
    );

CREATE TABLE IF NOT EXISTS user_file
(
    userId   INTEGER,
    fileId   SERIAL,
    fileName varchar(256),
    file     BYTEA,
    FOREIGN KEY (userId) REFERENCES users ON DELETE CASCADE,
    PRIMARY KEY (fileId)
    );

CREATE TABLE IF NOT EXISTS subject_files
(
    userId   INTEGER,
    fileId   SERIAL,
    fileName varchar(256),
    file     BYTEA,
    subjectid  INTEGER,
    subjectlevel INTEGER,
    FOREIGN KEY (userId) REFERENCES users ON DELETE CASCADE,
    FOREIGN KEY (subjectid) REFERENCES subject ON DELETE CASCADE,
    PRIMARY KEY (fileId)
    );

CREATE TABLE IF NOT EXISTS shared
(
    classId INTEGER,
    fileId  INTEGER,
    FOREIGN KEY (classId) REFERENCES classes ON DELETE CASCADE,
    FOREIGN KEY (fileId) REFERENCES subject_files ON DELETE CASCADE,
    PRIMARY KEY(classId, fileId)
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
    notifications INTEGER,
    studentlasttime TIMESTAMP,
    teacherlasttime TIMESTAMP,
    FOREIGN KEY (studentId) REFERENCES users ON DELETE CASCADE,
    FOREIGN KEY (teacherId) REFERENCES users ON DELETE CASCADE,
    FOREIGN KEY (subjectId) REFERENCES subject ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS userRoles
(
    roleId INTEGER NOT NULL,
    userId INTEGER NOT NULL,
    PRIMARY KEY (roleId, userId),
    FOREIGN KEY (userId) REFERENCES users ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS posts
(
    postId SERIAL PRIMARY KEY,
    type varchar(256),
    userId INTEGER NOT NULL,
    classId INTEGER NOT NULL,
    message varchar(256),
    file BYTEA,
    filename varchar(256),
    time TIMESTAMP NOT NULL,
    FOREIGN KEY (classId) REFERENCES classes ON DELETE CASCADE,
    FOREIGN KEY (userId) REFERENCES users ON DELETE CASCADE
    );
