CREATE TABLE IF NOT EXISTS users
(
    userId      BIGINT PRIMARY KEY,
    name        varchar(100),
    password    varchar(100),
    mail        varchar(100),
    description varchar(256) default '',
    schedule    varchar(256) default ''
);

CREATE TABLE IF NOT EXISTS rating
(
    teacherId bigint,
    userId bigint NOT NULL,
    rate float check (rate <= 5),
    review varchar(256),
    FOREIGN KEY (userId) REFERENCES users ON DELETE CASCADE,
    FOREIGN KEY (teacherId) REFERENCES users ON DELETE CASCADE,
    PRIMARY KEY (teacherId,userId)
);

CREATE TABLE IF NOT EXISTS favourites
(
    teacherId bigint,
    studentId bigint NOT NULL,
    FOREIGN KEY (studentId) REFERENCES users ON DELETE CASCADE,
    FOREIGN KEY (teacherId) REFERENCES users ON DELETE CASCADE,
    PRIMARY KEY (teacherId,studentId)
);

CREATE TABLE IF NOT EXISTS images
(
    userId bigint,
    image  bytea,
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
    userId    bigint,
    subjectId bigint,
    price     bigint,
    level     bigint,
    FOREIGN KEY (userId) REFERENCES users ON DELETE CASCADE,
    FOREIGN KEY (subjectId) REFERENCES subject ON DELETE CASCADE,
    PRIMARY KEY (userId, subjectId, level)
);

CREATE TABLE IF NOT EXISTS user_file
(
    userId   bigint,
    fileId   SERIAL,
    fileName VARCHAR,
    file     BYTEA,
    FOREIGN KEY (userId) REFERENCES users ON DELETE CASCADE,
    PRIMARY KEY (fileId)
);

CREATE TABLE IF NOT EXISTS subject_files
(
    userId   bigint,
    fileId   SERIAL PRIMARY KEY,
    fileName VARCHAR,
    file     BYTEA,
    subjectid  bigint,
    subjectlevel INTEGER,
    FOREIGN KEY (userid,subjectid,subjectlevel) REFERENCES teaches(userId, subjectId, level) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS shared
(
    classId bigint,
    fileId  bigint,
    FOREIGN KEY (classId) REFERENCES classes ON DELETE CASCADE,
    FOREIGN KEY (fileId) REFERENCES subject_files ON DELETE CASCADE,
    PRIMARY KEY(classId, fileId)
);

CREATE TABLE IF NOT EXISTS classes
(
    classId   SERIAL PRIMARY KEY,
    studentId bigint NOT NULL,
    teacherId bigint NOT NULL,
    level     INTEGER NOT NULL,
    subjectId bigint NOT NULL,
    price     INTEGER NOT NULL,
    status    INTEGER NOT NULL,
    deleted INTEGER default 0 NOT NULL,
    FOREIGN KEY (studentId) REFERENCES users ON DELETE CASCADE,
    FOREIGN KEY (teacherId) REFERENCES users ON DELETE CASCADE,
    FOREIGN KEY (subjectId) REFERENCES subject ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS roles
(
    roleId BIGINT PRIMARY KEY,
    role varchar(100)
    );

CREATE TABLE IF NOT EXISTS userRoles
(
    roleId BIGINT NOT NULL,
    userId BIGINT NOT NULL,
    PRIMARY KEY (roleId, userId),
    FOREIGN KEY (roleId) REFERENCES roles ON DELETE CASCADE,
    FOREIGN KEY (userId) REFERENCES users ON DELETE CASCADE
    );