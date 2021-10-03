CREATE TABLE IF NOT EXISTS users
(
    userId      SERIAL PRIMARY KEY,
    name        varchar(100),
    password    varchar(100),
    mail        varchar(100),
    description varchar(256) default '',
    schedule    varchar(256) default ''
);

CREATE TABLE IF NOT EXISTS rating
(
    teacherId INTEGER,
    userId INTEGER NOT NULL,
    rate float,
    review varchar(256),
    FOREIGN KEY (userId) REFERENCES users ON DELETE CASCADE,
    FOREIGN KEY (teacherId) REFERENCES users ON DELETE CASCADE,
    PRIMARY KEY (teacherId,userId)
);

CREATE TABLE IF NOT EXISTS images
(
    userId INTEGER,
    image  bytea,
    FOREIGN KEY (userId) REFERENCES users ON DELETE CASCADE,
    PRIMARY KEY (userId)
);

CREATE TABLE IF NOT EXISTS subject
(
    subjectId SERIAL PRIMARY KEY,
    name      varchar(100)
);

CREATE TABLE IF NOT EXISTS timetable
(
    userId    INTEGER,
    monday    VARCHAR(100),
    tuesday   VARCHAR(100),
    wednesday VARCHAR(100),
    thursday  VARCHAR(100),
    friday    VARCHAR(100),
    saturday  VARCHAR(100),
    sunday    VARCHAR(100),
    FOREIGN KEY (userId) REFERENCES users ON DELETE CASCADE,
    PRIMARY KEY (userId)
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
    request varchar(256) default '',
    reply    varchar(256) default '',
    FOREIGN KEY (studentId) REFERENCES users ON DELETE CASCADE,
    FOREIGN KEY (teacherId) REFERENCES users ON DELETE CASCADE,
    FOREIGN KEY (subjectId) REFERENCES subject ON DELETE CASCADE
);

CREATE OR REPLACE VIEW front_classes AS
(
SELECT classId,  student.name AS studentName, teacher.name AS teacherName, level, status,
    price, subject.name AS subjectName, classes.request AS request, classes.reply AS reply
FROM classes LEFT JOIN users student ON student.userId = classes.studentId
         LEFT JOIN users teacher ON teacher.userid = classes.teacherid
         LEFT JOIN subject on classes.subjectid = subject.subjectid
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

